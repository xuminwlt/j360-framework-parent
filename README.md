# j360-framework


[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/xuminwlt/j360-framework-parent.svg?branch=master)](https://travis-ci.org/xuminwlt/j360-framework-parent)
[![](https://jitpack.io/v/xuminwlt/j360-framework-parent.svg)](https://jitpack.io/#xuminwlt/j360-framework-parent)

## How to Use

使用github repo
```
<repositories>
        <repository>
            <id>maven-repo-github-releases</id>
            <url>https://raw.github.com/xuminwlt/github-repo/master/releases</url>
        </repository>
        <repository>
            <id>maven-repo-github-snapshots</id>
            <url>https://raw.github.com/xuminwlt/github-repo/master/snapshots</url>
        </repository>
</repositories>
```

或者使用jit repo
```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

```
<dependency>
	    <groupId>com.github.xuminwlt</groupId>
	    <artifactId>j360-framework-base</artifactId>
	    <version>1.0</version>
</dependency>
```

或者使用私服 maven:deploy
```
<distributionManagement>
        <repository>
            <id>nexus</id>
            <name>Packaging Release Repository</name>
            <url>http://xxx.xxx.xxx.xxx/nexus/content/repositories/releases</url>
        </repository>
        <snapshotRepository>
            <id>nexus</id>
            <name>Packaging Snapshot Repository</name>
            <url>http://xxx.xxx.xxx.xxx/nexus/content/repositories/snapshots</url>
        </snapshotRepository>
    </distributionManagement>

```

## Modules

- j360-base: 二方Base库,用于同一相关规范,二方包精简去除外部无关依赖
- j360-core: 核心lib: 工具包, 依赖vjtool
- j360-common: 通用类, 用于简化一方工程构建和优化程序, 定位三方依赖库
- j360-boot-starts: TODO, 快速构建Spring Boot Configuration
- j360-boot-starts-web: TODO, 进一步快速构建Spring Boot Web Configuration
- example-web: Shiro Example(Shiro + jwt + redis)
 
## Samples

### j360-boot-app-all

https://github.com/xuminwlt/j360-boot-app-all


