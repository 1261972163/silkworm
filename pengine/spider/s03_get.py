import urllib
import urllib2

# 构造request的参数
values = {}
values['username'] = "1729374585@qq.com"
values['password'] = "XXXX"
data = urllib.urlencode(values)
url = "http://passport.csdn.net/account/login?from=http://my.csdn.net/my/mycsdn"
# get方式
request = urllib2.Request("%s?%s" % (url, data))
response = urllib2.urlopen(request)
print response.read()
