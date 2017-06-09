�����˿������˽��Velocity���������淭��Ϊ���ٶȡ����ʡ�Ѹ�٣�����Web������ù����˿��ܲ��࣬�󶼻���֪������ʹ��Struts������ Velocity��Struts�������ϵ����ô����Velocity�أ�������������һ�£��˽�Velocity�ĸ��ͨ��������Ľ��ܣ�ǿ���ڼ���ѡ���ϵ����⣬�ô����ѡ����Ŀ����ʱ�����Կ���Velocity������Ҳ�ô���˽�����˼�룬�Ͼ����ṩ��һ���ܺõ�˼ά��ʽ������һ�����ǣ���һ��˼���ķ�ʽ��
���Ļ������Java������һ��������֪��MVC��Struts�ȿ���ģʽ��
Velocity��һ��Javaģ�����漼��������Ŀ��Apache�����������һ�����漼��Webmacro�����������ʲô�ǹٷ���Velocity�����أ�Apache�����Ķ����ǣ�һ�ֻ���Java��ģ�����棬�������κ���ʹ�ü򵥶�ǿ���ģ�����������ö�����Java�����еĶ���Ŀǰ���µİ汾��1.4��������http://jakarta.apache.org/velocity/index.html���Ҹ�����Ϣ��
��ʵ˵����VelocityҲ����MVC�ܹ���һ��ʵ�֣�����������ǹ�ע��Model�� View֮�䣬��Ϊ���ǵ�����������MVC�������мܹ�Struts��˵�����Ŵ�Ҷ���İ�����ܶ࿪����Ա�Ѿ�������ʹ��Struts�ܹ�������IBM�� Websphere 5���ϵĹ���ƽ̨�汾��Struts�����ܺõ�ʵ����MVC������Ч�ļ���Java������View��Jsp���еĳ��֣�����Model��View֮�仹������Struts��Taglib������ʵ�֣��������ǰ̨��������ҳ���ʦ��Struts����Taglib���죨����Ҳͦ����ģ��������ڵ�ά����ԱҲһ�������������ҳ���ʦ��ǰ̨��������ʦ���໥Э�����������ܴ���Ѷȣ���ʵ������Ҳ���Ǵ���������ʵ����ҳ���ʦ��ǰ̨����֮��Ĺ��������ٻ��Ǵ���һ������ϣ���������޶ȵĽ����������أ�����������������Velocity����˵�������ɡ�
����һ����򵥵�Velocity�������ӣ��ô�ҿ���Velocity�����������ģ�
1������1���ļ����ļ���Ϊ��hellovelocity.vm����velocityģ�棨��ʵ��htmlһ���������ݣ�
Welcome $name to Javayou.com!

today is $date.
2������1��java�ļ���HelloVelocity.java�����ݣ�
package com.javayou.velocity;

import java.io.StringWriter;
import java.util.*;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class HelloVelocity {
    public static void main(String[] args) throws Exception {
        //��ʼ����ȡ��Velocity����
        VelocityEngine ve = new VelocityEngine();
        ve.init();

        //ȡ��velocity��ģ��
        Template t = ve.getTemplate("hellovelocity.vm");

        //ȡ��velocity��������context
        VelocityContext context = new VelocityContext();

        //����������������
        context.put("name", "Liang");
        context.put("date", (new Date()).toString());

        //Ϊ�����չʾ����ǰ����List��ֵ
        List temp = new ArrayList();
        temp.add("1");
        temp.add("2");
        context.put("list", temp);

        //�����
        StringWriter writer = new StringWriter();

        //ת�����
        t.merge(context, writer);

        System.out.println(writer.toString());
    }
}
3����http://jakarta.apache.org/site/binindex.cgi������Velocity 1.4 zip����ѹ���ȡvelocity-1.4.jar�������������������HelloVelocity.java��
4����1�ϵ�hellovelocity.vm copy�����еĵ�ǰĿ¼�£�����HelloVelocity����Ҫ������������Դ����غ��velocity1.4.zip����\velocity- 1.4\build\lib����commons-collections.jar��logkit-1.0.1.jar���������java -cp .\bin; -Djava.ext.dirs=.\lib2 com.javayou.velocity.HelloVelocity������class���뵽.\binĿ¼�����������������ŵ�.\lib2Ŀ¼�ڣ����нṹ���£�
Welcome Liang to Javayou.com!

today is Tue Dec 14 19:26:37 CST 2004.
��������򵥵����н������ô����֪������Űɣ�ģ��hellovelocity.vm��� 2���������$name��$date�ֱ�context.put("name", "Liang")��context.put("date", (new Date()).toString())�����ֵ����ˡ�
�ɴ˿���ҵ�����̴������ҵ����������model���ȫ���������view��һ�����ֻ��ʹ�ü򵥵�VTL��Velocity Template Language����չʾ��������Jsp���ǲ�����ô���ǵģ�������ʹ��ģʽ�е�����ǰ��CGI��ʽ������Velocity�Զ�������룬���� Velocity���ⷽ�������Ҳ��ǿ��Turbine��Ͳ�����Velocity�������ܶ���롣
��Velocity�У������Ķ��嶼��ʹ�á�$����ͷ�ģ�$��ΪVelocity�ı�ʶ������ĸ�����֡��л����»��߶�������ΪVelocity�Ķ��������
�������ǻ���Ҫע�����Velocity��ɫ�ı������壬�磺$student.No��$student.Address������2�㺬�壺��1�������student��hashtable���򽫴� hashtable����ȡkeyΪNo��Address��ֵ�������2�־������п����ǵ��÷�����������2����������ת��Ϊ student.getNo()��student.getAddress()��Velocity����servlet�е�java code���ص�ֵ�ж��󣬻����Ե��ö���ķ�������$ student.getAddress()�ȵȣ��ڴ˾Ͳ�һһ�����������ˡ�
���������ֻ�Ǽ򵥵ľ��������ڵ�Ȼ�������Ѿ������������������ˣ�ʵ�ʵ�Ӧ�������ǻ�������Ҫ��Щѡ����չʾ���о�һЩ�������ݣ���List�б���ȻVelocity��������˵Ӧ����VTLģ�����ԣ�Ҳ֧������ܣ����⻹֧������һЩ���õ�չʾ����ģ���ڲ��ı�������Jsp�ڵı�����������ǿ��һЩ���紴������ʵ���Զ����������Ǽ����������¿��ɡ�
���ǻ���ʹ����������ӣ���ģ��hellovelocity.vm�е����ݸ�Ϊ��
#set( $iAmVariable = "good!" )

Welcome $name to Javayou.com!

today is $date.

$iAmVariable
����ִ�������������������
Welcome Liang to Javayou.com!

today is Tue Dec 14 22:44:39 CST 2004.

good!