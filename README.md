# docs
客官，进来看看嘛。
其中Redis的相关的配置请注意
window启动redis服务端端 
1、下载的文件中的redis.windows.conf文件中添加：
bind 127.0.0.1
maxmemory 209715200

2、cmd 先跳到其安装目录使用命令启动：redis-server.exe redis.windows.conf


启动客户端redis-cli.exe
查看所有的 keys-------------------- keys *
清空所有的key机器数据 -------flushall

----------------------------------
2018/8/12新添加websocket , 邮箱回复