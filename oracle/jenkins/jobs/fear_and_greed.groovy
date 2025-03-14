pipelineJob('fear-and-greed') {
    displayName('fear-and-greed')
    description('fear-and-greed')
    definition {
        cps {
            script(
                '''
                pipeline {
                    agent any
                    stages {
                        stage('Get fear and greed index') {
                            steps {
                                sh \"\"\"
                                    apt update
                                    apt install -y jq
                                    curl 'https://production.dataviz.cnn.io/index/fearandgreed/graphdata' \\
                                      -H 'Accept: */*' \\
                                      -H 'Accept-Language: zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7' \\
                                      -H 'Connection: keep-alive' \\
                                      -H 'DNT: 1' \\
                                      -H 'Origin: https://edition.cnn.com' \\
                                      -H 'Referer: https://edition.cnn.com/' \\
                                      -H 'Sec-Fetch-Dest: empty' \\
                                      -H 'Sec-Fetch-Mode: cors' \\
                                      -H 'Sec-Fetch-Site: cross-site' \\
                                      -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36' \\
                                      -H 'sec-ch-ua: "Chromium";v="134", "Not:A-Brand";v="24", "Google Chrome";v="134"' \\
                                      -H 'sec-ch-ua-mobile: ?0' \\
                                      -H 'sec-ch-ua-platform: "macOS"' -o result.json
                                    score=$(cat result.json | jq .fear_and_greed.score)
                                    rating=$(cat result.json | jq .fear_and_greed.rating)
                                    curl -X POST -d "Fear and Greed Index Score: $score, Rating: $rating" https://ntfy.haijin666.top/haijin
                                \"\"\"
                            }
                        }
                    }
                }
                '''
            )
        }
    }
}
