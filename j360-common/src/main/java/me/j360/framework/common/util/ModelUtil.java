package me.j360.framework.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ma.glasnost.orika.metadata.Type;
import me.j360.framework.base.domain.BaseDO;
import me.j360.framework.base.domain.model.BaseLongID;
import me.j360.framework.base.domain.model.BaseModel;
import me.j360.framework.common.pool.DefaultExecutor;
import me.j360.framework.core.kit.mapper.orika.BeanMapper;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * User: min_xu
 * Date: 2017/6/22 下午6:42
 * 说明：
 */
public class ModelUtil {


    /**
     * 合并两个对象,通过ID进行绑定
     * @param fullCollection
     * @param subCollection
     * @param <D>
     * @param <S>
     * @return
     */
    public static <D extends BaseLongID, S extends BaseLongID>  Collection<D> combinCollection(Collection<D> fullCollection, Collection<S> subCollection) {
        Map<Long, S> map = subCollection.stream().collect(
                Collectors.toMap(S::getId,(p) -> p));

        fullCollection.stream().forEach(p -> {
            S s = map.get(p.getId());
            if (Objects.nonNull(s)) {
                BeanMapper.map(s, p);
            }
        });

        return fullCollection;
    }

    /**
     *
     * @param fullCollection
     * @param subCollection
     * @param destinationType  BeanMapper.getType(A.class)
     * @param sourceType  BeanMapper.getType(A.class)
     * @param <D>
     * @param <S>
     */
    public static <D extends BaseLongID, S extends BaseLongID>  void combinCollection(Collection<D> fullCollection, Collection<S> subCollection, Type<D> destinationType, Type<S> sourceType) {
        Map<Long, S> map = subCollection.stream().collect(
                Collectors.toMap(S::getId, (p) -> p));

        fullCollection.stream().forEach(p -> {
            S s = map.get(p.getId());
            if (Objects.nonNull(s)) {
                BeanMapper.map(s, p, sourceType, destinationType);
            }
        });
    }


    public static <D extends BaseLongID, S extends BaseLongID, T extends BaseLongID>  Collection<D> combinCollection3(Collection<D> fullCollection, Collection<S> subCollection, Collection<T> subCollection2) {
        Map<Long,S> map = subCollection.stream().collect(
                Collectors.toMap(S::getId,(p) -> p));

        Map<Long,T> map2 = subCollection2.stream().collect(
                Collectors.toMap(T::getId,(p) -> p));

        fullCollection.stream().forEach(p -> {
            S s = map.get(p.getId());
            if (Objects.nonNull(s)) {
                BeanMapper.map(s, p);
            }

            T t = map2.get(p.getId());
            if (Objects.nonNull(t)) {
                BeanMapper.map(t, p);
            }
        });

        return fullCollection;
    }


    public static <D extends BaseLongID, S extends BaseLongID, T extends BaseLongID>  void combinCollection3(Collection<D> fullCollection, Collection<S> subCollection, Collection<T> subCollection2, Type<D> destinationType, Type<S> sourceType, Type<T> sourceType2) {
        Map<Long,S> map = subCollection.stream().collect(
                Collectors.toMap(S::getId,(p) -> p));

        Map<Long,T> map2 = subCollection2.stream().collect(
                Collectors.toMap(T::getId,(p) -> p));

        fullCollection.stream().forEach(p -> {
            S s = map.get(p.getId());
            if (Objects.nonNull(s)) {
                BeanMapper.map(s, p, sourceType, destinationType);
            }

            T t = map2.get(p.getId());
            if (Objects.nonNull(t)) {
                BeanMapper.map(t, p, sourceType2, destinationType);
            }
        });
    }



    public static <T extends BaseLongID> Map<Long,T> toMap(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<Long,T> map = list.stream().collect(
                Collectors.toMap(T::getId,(p) -> p));
        return map;
    }


    /**
     * 对List<CompletableFuture> 解析成 List<T>
     * @param futures
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<List<T>> listFuture(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.<T>toList()));
    }


    /**
     * 批量提交任务TODO 待测
     * @param suppliers
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<List<T>> listSuppliers(List<Supplier<T>> suppliers) {
        List<CompletableFuture<T>> futures = Lists.newArrayListWithCapacity(suppliers.size());
        suppliers.stream().forEach(supplier -> {
            futures.add(DefaultExecutor.supplyAsync(supplier));
        });
        return listFuture(futures);
    }

    /**
     * 批量提交任务返回结果
     * @param suppliers
     * @param <T>
     * @return
     */
    public static <T> List<T> listSuppliersResults(List<Supplier<T>> suppliers) {
        List<CompletableFuture<T>> futures = Lists.newArrayListWithCapacity(suppliers.size());
        suppliers.stream().forEach(supplier -> {
            futures.add(DefaultExecutor.supplyAsync(supplier));
        });
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.<T>toList())).join();
    }

    public static <T> List<T> listDefaultSuppliersResults(List<Supplier<T>> suppliers) {
        List<CompletableFuture<T>> futures = Lists.newArrayListWithCapacity(suppliers.size());
        suppliers.stream().forEach(supplier -> {
            futures.add(DefaultExecutor.supplyDefultAsync(supplier));
        });
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.<T>toList())).join();
    }

    /**
     * 获取多个redis的keys
     * @param format
     * @param ids
     * @return
     */
    public static String[] formatStrings(String format, List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new String[0];
        }
        List<String> formatList = ids.stream().map( s -> {
            return String.format(format, s);
        }).collect(Collectors.toList());
        return formatList.toArray(new String[formatList.size()]);
    }


    /*************
     * 通用的转化类
     *
     *
     List<AuthorDto> authorDtos = ModelUtil.createDtos(authorDOs, (t) -> {
         return ModelConvertUtils.createAuthorDto(t);
     });

     public static AuthorDto createAuthorDto(AuthorDO authorDO) {
        AuthorDto dto = null;
        if (Objects.nonNull(authorDO)) {
            dto = new AuthorDto();
            dto.setId(authorDO.getId());
        }
        return dto;
     }
     * @param dos
     * @param function
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R extends BaseDO, T extends BaseModel> List<R> createDtos(List<T> dos, Function<T, R> function) {
        if (Objects.nonNull(dos)) {
            final List<R> dtos = Lists.newArrayListWithExpectedSize(dos.size());
            dos.stream().forEach(en -> {
                dtos.add(function.apply(en));
            });
            return dtos;
        }
        return null;
    }
}
