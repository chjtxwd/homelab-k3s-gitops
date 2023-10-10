# 使用 Alpine Linux 作为基础镜像
FROM alpine:latest

# 设置工作目录
WORKDIR /app

# 下载并解压 nps 客户端
RUN apk add --no-cache curl tar \
    && curl -L -o nps.tar.gz https://github.com/ehang-io/nps/releases/download/v0.26.10/linux_arm64_client.tar.gz \
    && tar -xzf nps.tar.gz \
    && rm nps.tar.gz

# 设置环境变量，用于传递参数
ENV NPSSERVER ""
ENV NPSVKEY ""

# 定义启动命令
CMD ["./npc", "-server=$NPSSERVER", "-vkey=$NPSVKEY", "-type=tcp"]
