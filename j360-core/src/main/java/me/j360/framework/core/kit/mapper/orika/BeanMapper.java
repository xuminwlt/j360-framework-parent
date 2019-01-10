package me.j360.framework.core.kit.mapper.orika;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;

import java.util.List;

/**
 * 简单封装orika, 实现深度的BeanOfClasssA<->BeanOfClassB复制
 * 
 * 不要是用Apache Common BeanUtils进行类复制，每次就行反射查询对象的属性列表, 非常缓慢.
 * 
 * 注意: 需要参考本模块的POM文件，显式引用orika.
 *
 * orika性能比Dozer快近十倍，也不需要Getter函数与无参构造函数
 *
 * 但我们内部修复了的bug，社区版没有修复: https://github.com/orika-mapper/orika/issues/252
 *
 * 如果应用启动时有并发流量进入，可能导致两个不同类型的同名属性间(如Order的User user属性，与OrderVO的UserVO user)的复制失败，只有重启才能解决。
 *
 */
public class BeanMapper {

	private static MapperFacade mapper;

	static {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapper = mapperFactory.getMapperFacade();
	}

	/**
	 * 简单的复制出新类型对象.
	 * 
	 * 通过source.getClass() 获得源Class
	 */
	public static <S, D> D map(S source, Class<D> destinationClass) {
		return mapper.map(source, destinationClass);
	}

	/**
	 *
	 * @param source
	 * @param destination
	 * @param <S>
     * @param <D>
     */
	public static <S, D> void map(S source, D destination) {
		 mapper.map(source, destination);
	}

	/**
	 * 极致性能的复制出新类型对象.
	 * 
	 * 预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
	 */
	public static <S, D> D map(S source, Type<S> sourceType, Type<D> destinationType) {
		return mapper.map(source, sourceType, destinationType);
	}

	/**
	 * 极致性能的复制出新类型对象.
	 *
	 * 预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
	 */
	public static <S, D> void map(S source, D destination, Type<S> sourceType, Type<D> destinationType) {
		mapper.map(source,destination, sourceType, destinationType);
	}

	/**
	 * 简单的复制出新对象列表到ArrayList
	 * 
	 * 不建议使用mapper.mapAsList(Iterable<S>,Class<D>)接口, sourceClass需要反射，实在有点慢
	 */
	public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<S> sourceClass, Class<D> destinationClass) {
		return mapper.mapAsList(sourceList, TypeFactory.valueOf(sourceClass), TypeFactory.valueOf(destinationClass));
	}

	/**
	 * 极致性能的复制出新类型对象到ArrayList.
	 * 
	 * 预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
	 */
	public static <S, D> List<D> mapList(Iterable<S> sourceList, Type<S> sourceType, Type<D> destinationType) {
		return mapper.mapAsList(sourceList, sourceType, destinationType);
	}

	/**
	 * 简单复制出新对象列表到数组
	 * 
	 * 通过source.getComponentType() 获得源Class
	 */
	public static <S, D> D[] mapArray(final D[] destination, final S[] source, final Class<D> destinationClass) {
		return mapper.mapAsArray(destination, source, destinationClass);
	}

	/**
	 * 极致性能的复制出新类型对象到数组
	 * 
	 * 预先通过BeanMapper.getType() 静态获取并缓存Type类型，在此处传入
	 */
	public static <S, D> D[] mapArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType) {
		return mapper.mapAsArray(destination, source, sourceType, destinationType);
	}

	/**
	 * 预先获取orika转换所需要的Type，避免每次转换.
	 */
	public static <E> Type<E> getType(final Class<E> rawType) {
		return TypeFactory.valueOf(rawType);
	}

}