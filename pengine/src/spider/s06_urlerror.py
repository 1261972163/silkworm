import urllib2

requset = urllib2.Request('http://www.800bset.com')

try:
    response = urllib2.urlopen(requset)
    print "response=%s" % response.read()
except urllib2.URLError, e:
    print "error=%s" % e.reason