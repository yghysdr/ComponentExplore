## 组件化探索

### 需要解决的问题
- 模块接口的暴露
- 模块间隔离
- 模块间通讯
- 模块分层
- Application需要在每个模块单独使用的
- 打包
- 子模块自动管理(引入/删除)功能

### 模块接口的暴露
#### 暴露内容包含
- 业务模块需要暴露的接口
- 业务模块需要暴露给外部使用的实体

#### 暴露方式
> 生成一份与业务模块对应的业务接口模块，将需要暴露的内容放在指定目录。建议不要手动修改接口模块的内容，将接口模块添加到忽略文件，使用者只需要编译下项目即可自动生成。

在Setting.gradle使用include_module_api(需要暴露的模块)
```
def include_module_api(String moduleName) {
    def ModuleApiName = "business_api"
    //获得工程根目录
    def originFile = project(moduleName).projectDir
    def originDir = originFile.path
    println "OriginDir=" + originDir
    def targetDir = "${originFile.getParent()}${File.separator}$ModuleApiName${File.separator}${originFile.getName()}_api"
    //制作的 SDK 工程的目录
    println "TargetDir=" + targetDir
    //制作的 SDK 工程的名字
    String sdkName = "${project(moduleName).name}_api"
    //第二次生成之前先删除上次的文件(iml)
    FileTree targetFiles = fileTree(targetDir)
    targetFiles.exclude "*.iml"
    targetFiles.each { File file ->
        file.delete()
    }
    //从待制作SDK工程拷贝目录到 SDK工程 只拷贝目录
    copy {
        from originDir
        into targetDir
        //拷贝文件
        include '**/api/**'
        include '**/AndroidManifest.xml'
        include 'build.gradle'
    }
    def originManifest = fileTree(targetDir).exclude("**/build").find {
        it.name == "AndroidManifest.xml"
    }
    def parser = new XmlParser().parse(originManifest)
    parser.setValue([])
    new XmlNodePrinter(new PrintWriter(originManifest)).print(parser)
    //加入 SDK工程
    include ":$ModuleApiName:$sdkName"
}
```

### 模块间通讯
> 这个使用aar的形式（建议通过maven从而引入版本控制）。使用路由框架，业务模块不直接依赖，但是业务模块暴露的api中包含业务实现的具体路径，也就能进行通讯

### 模块间隔离
> 编译时依赖接口 module-api， 运行时依赖module

业务模块间通讯通过compileOnly依赖其他模块的module_api
```
compileOnly project(":module_login_api")
```
主工程依赖
```
runtimeOnly project(":module_login")
```

### 模块间隔离
最好的方式是绝对不产生依赖关系，最终通过的一个打包的module来引入对应的实现module

### 模块分层
> 从顶层到业务划分如下，依赖方式只能是业务向顶层的方向依赖，一定不能产生反方向的依赖

- 基础组建
- 功能模块，包含对应的配置
- 业务模块和业务接口模块
- 打包模块


### 打包
创建一个application的工程，通过runtimeOnly引入所有的业务module（指的是具体业务模块，不包含module_api）
```
com.android.application
```
其他所有非打包模块是
```
apply plugin: 'com.android.library'
```

### 子模块自动管理(引入/删除)功能
我们期望项目中只引入自己业务相关的子模块，而且当不使用的时候自动删除。引入script目录，配置module_infos.gradle和module_config.gradle
- module_infos.gradle是配置子项目依赖和源码信息
- module_config.gradle是默认的配置依赖关系（项目会默认拷贝到根目录，建议将根目录生成的该文件添加到忽略文件，方便自定义配置）
  - git_branch 表示依赖源码时仓库的分支
  - style代表依赖方式 1源码，2aar, 21aar依赖并且删除源文件【需要谨慎使用】, 0不引入
在setting.gradle 中引入
```
apply from: 'script/lib_config.gradle'
```
在根工程build.gradle 中引入
```
apply from: 'script/root_config.gradle'
```