不少人看过或了解过Velocity，名称字面翻译为：速度、速率、迅速，用在Web开发里，用过的人可能不多，大都基本知道和在使用Struts，到底 Velocity和Struts是如何联系，怎么看待Velocity呢？让我们来尝试一下，了解Velocity的概念，通过在这里的介绍，强调在技术选择上的问题，让大家在选择项目开发时，可以考虑Velocity，另外也让大家了解它的思想，毕竟它提供了一个很好的思维方式，给大家换换筋骨，换一种思考的方式。
本文基于你对Java开发有一定基础，知道MVC，Struts等开发模式。
Velocity是一种Java模版引擎技术，该项目由Apache提出，由另外一种引擎技术Webmacro引深而来。那什么是官方的Velocity定义呢？Apache对它的定义是：一种基于Java的模板引擎，但允许任何人使用简单而强大的模板语言来引用定义在Java代码中的对象。目前最新的版本是1.4，可以在http://jakarta.apache.org/velocity/index.html查找更多信息。
其实说白了Velocity也就是MVC架构的一种实现，但它更多的是关注在Model和 View之间，作为它们的桥梁。对于MVC的最流行架构Struts来说，相信大家都不陌生，很多开发人员已经大量在使用Struts架构，包括IBM的 Websphere 5以上的管理平台版本，Struts技术很好的实践了MVC，它有效的减少Java代码在View（Jsp）中的出现，但在Model和View之间还是依靠Struts的Taglib技术来实现，试想如果前台开发的网页设计师对Struts乃至Taglib不熟（相信也挺难熟的，包括后期的维护人员也一样），将会对网页设计师和前台开发工程师的相互协作开发带来很大的难度，现实开发中也还是存在这样事实，网页设计师和前台开发之间的工作或多或少还是存在一定的耦合，怎样最大限度的解决这个难题呢？还是让我们来看看Velocity或者说这个概念吧。
先做一个最简单的Velocity开发例子，让大家看看Velocity是怎样工作的：
1、创建1个文件，文件名为：hellovelocity.vm，即velocity模版（其实和html一样），内容：
Welcome $name to Javayou.com!

today is $date.
2、创建1个java文件，HelloVelocity.java，内容：
package com.javayou.velocity;

import java.io.StringWriter;
import java.util.*;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class HelloVelocity {
    public static void main(String[] args) throws Exception {
        //初始化并取得Velocity引擎
        VelocityEngine ve = new VelocityEngine();
        ve.init();

        //取得velocity的模版
        Template t = ve.getTemplate("hellovelocity.vm");

        //取得velocity的上下文context
        VelocityContext context = new VelocityContext();

        //把数据填入上下文
        context.put("name", "Liang");
        context.put("date", (new Date()).toString());

        //为后面的展示，提前输入List数值
        List temp = new ArrayList();
        temp.add("1");
        temp.add("2");
        context.put("list", temp);

        //输出流
        StringWriter writer = new StringWriter();

        //转换输出
        t.merge(context, writer);

        System.out.println(writer.toString());
    }
}
3、在http://jakarta.apache.org/site/binindex.cgi上下载Velocity 1.4 zip，解压后获取velocity-1.4.jar，用它来编译上面的类HelloVelocity.java。
4、把1上的hellovelocity.vm copy到运行的当前目录下，运行HelloVelocity还需要其他类包，可以从下载后的velocity1.4.zip来，\velocity- 1.4\build\lib，把commons-collections.jar、logkit-1.0.1.jar引入后运行java -cp .\bin; -Djava.ext.dirs=.\lib2 com.javayou.velocity.HelloVelocity，假设class编译到.\bin目录，而我们所需的类包放到.\lib2目录内，运行结构如下：
Welcome Liang to Javayou.com!

today is Tue Dec 14 19:26:37 CST 2004.
以上是最简单的运行结果，怎么样，知道个大概吧，模版hellovelocity.vm里的 2个定义变量$name和$date分别被context.put("name", "Liang")和context.put("date", (new Date()).toString())所设的值替代了。
由此看来业务流程处理包括业务结果基本在model这层全部解决，而view这一层基本只用使用简单的VTL（Velocity Template Language）来展示。这样，Jsp岂不是不用了么？是的，这样的使用模式有点象早前的CGI方式：）由Velocity自动输出代码，并且 Velocity在这方面的能力也很强，Turbine里就采用了Velocity来产生很多代码。
在Velocity中，变量的定义都是使用“$”开头的，$作为Velocity的标识符。字母、数字、中划和下划线都可以作为Velocity的定义变量。
此外我们还需要注意的是Velocity特色的变量定义，如：$student.No、$student.Address，它有2层含义：第1种是如果student是hashtable，则将从 hashtable中提取key为No和Address的值，另外第2种就是它有可能是调用方法，即上面2个变量将被转换为 student.getNo()和student.getAddress()。Velocity对在servlet中的java code返回的值有对象，还可以调用对象的方法，如$ student.getAddress()等等，在此就不一一举例和深入了。
上面的例子只是简单的举例，现在当然不少人已经不满足这样的例子了，实际的应用中我们还常常需要作些选择性展示和列举一些迭代数据，如List列表，当然Velocity（具体来说应该是VTL模版语言）也支持这项功能，此外还支持其他一些常用的展示，如模版内部的变量（如Jsp内的变量），还有强大一些的如创建宏以实现自动化，让我们继续接着往下看吧。
我们还是使用上面的例子，把模版hellovelocity.vm中的内容改为：
#set( $iAmVariable = "good!" )

Welcome $name to Javayou.com!

today is $date.

$iAmVariable
重新执行上面的运行命令，结果：
Welcome Liang to Javayou.com!

today is Tue Dec 14 22:44:39 CST 2004.

good!