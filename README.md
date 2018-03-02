# favorite
> analysis, search and classify for Bookmarks

* 集成log4j,处理jetty日志	√
* 添加忽略的文件及路径		√
* 集成es					√
* 一个简单的UI页面		√
* JSTOM自动分析
* 搜索建议，纠错
* 一个更简单的交互页面

### 文档格式
```
curl -XPUT '192.168.253.104:9200/bookmark?pretty' -H 'Content-Type: application/json' -d'
{
     "settings": {
          "number_of_shards": 1
     },
     "mappings": {
          "doc": {
               "_all": {
                    "enabled": false
               },
               "properties": {
                    "name": {
                         "type": "text",
                         "index": "analyzed",
                         "fields": {
                              "cn": {
                                   "type": "text",
                                   "analyzer": "ik_max_word"
                              },
                              "en": {
                                   "type": "text",
                                   "analyzer": "english"
                              }
                         }
                    },
                    "url": {
                         "type": "keyword"
                    },
                    "createDate": {
                         "type": "date",
                         "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
                    }
               }
          }
     }
}
'
```