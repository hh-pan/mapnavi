# mapnavi
百度地图定位，导航功能

### 创建应用
http://lbsyun.baidu.com/
打开百度地图开放平台，去控制台创建应用，填写应用信息

http://lbs.baidu.com/index.php?title=sdk/download&action#selected=mapsdk_basicmap,mapsdk_searchfunction,mapsdk_lbscloudsearch,mapsdk_calculationtool,mapsdk_radar
根据需要的功能下载开发资源

### 创建签名文件 

F:\WORK\mine\workspace\baiduNav>keytool -genkey -alias map -keypass 123456 -keyalg RSA -keysize 1024 -validity 3650 -keystore F:\mapkey.keystore -storepass 123456

### 查看SHA1

F:\WORK\mine\workspace\baiduNav>keytool -list -v -keystore F:\mapkey.keystore
