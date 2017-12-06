import urllib
import urllib2

# 构造request的参数
values = {"username":"1729374585@qq.com","password":"XXXX"}
data = urllib.urlencode(values)
url = "https://passport.csdn.net/account/login?from=http://my.csdn.net/my/mycsdn"
# post方式
request = urllib2.Request(url,data)
response = urllib2.urlopen(request)
print response.read()