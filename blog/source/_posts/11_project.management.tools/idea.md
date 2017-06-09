idea IntelliJ常用配置

	1 常用插件
	2 常用快捷键
	3 其他

1 常用插件

	Identifier Highlighter： 高亮显示选中变量
	Key Promoter： 快捷键提示
	Jrebel： 热部署插件。
	破解地址：http://blog.csdn.net/sunny243788557/article/details/33688795
	FindBugs for IntelliJ IDEA： 通过FindBugs帮你找到隐藏的bug及不好的做法
	TabSwitch： 通过ctrl+tab在文件，各个面板tab间切换
	Mybatis： Mybatis插件
	UpperLowerCapitalize： 大小写转换插件。快捷键alt+P全部大写，alt+L全部小写，alt+C开头字母大写
	generate serialversionuid： 生成uuid的插件。快捷键 alt+insert
	idea vim： 模拟vi编辑器命令
	generateO2O：自动对象转换插件
	IdeaJad：反编译
	EncodingPlugin： 可按项目指定其默认编码
	Equals and hashCode： 重写equals和hashcode方法的自定义模板
	unitTest： 在指定的方法上按下shift+cmd+t 即可为这个方法生成单元测试代码模板。
	FileBrowser： 在IDEA中查看项目外的文件
	GenerateToString： 自动生成toString方法, toString方法是可定制的
	
2 常用快捷键

1 智能提示

	Ctrl+Space，代码提示（与系统输入法快捷键冲突）
	Ctrl+Shift+Space，更智能地按类型信息提示，但因为Intellij总是随着我们敲击而自动提示，所以很多时候都不会手动敲这两个快捷键(除非提示框消失了)。
	F2/Shift+F2，移动到有错误的代码
	Alt+Enter，快速修复。当智能提示为我们自动补全方法名时，我们通常要自己补上行尾的反括号和分号，当括号嵌套很多层时会很麻烦，这时我们只需敲Ctrl+Shift+Enter就能自动补全末尾的字符。而且不只是括号，例如敲完if/for时也可以自动补上{}花括号。

2 重构

	Ctrl+Shift+Alt+T，重构功能大汇总菜单
	Shift+F6，重命名
	Alt+Delete，安全删除

3 代码生成

	Ctrl+J，自动代码（例如：fori/sout/psvm+Tab，生成循环，System.out、main方法等）
	后面“辅助”一节中将会讲到Alt+Insert，在编辑窗口中点击可以生成构造函数、toString、getter/setter、重写父类方法等。
	这两个技巧实在太常用了，几乎每天都要生成一堆main、System.out和getter/setter。
	另外，Intellij IDEA 13中加入了后缀自动补全功能(Postfix Completion)，比模板生成更加灵活和强大。例如要输入for(User user : users)只需输入user.for+Tab。再比如，要输入Date birthday = user.getBirthday();只需输入user.getBirthday().var+Tab即可。
	Ctrl+O，重写方法
	Ctrl+/或Ctrl+Shift+/，注释（//或者/**/）
	Ctrl+Alt+J，用动态模板环绕
	Tab，代码标签输入完成后，按 Tab，生成代码
	Ctrl+Shift+Space，自动补全代码
	Ctrl+Alt+Space，类名自动完成
	Ctrl+Alt+O，优化导入的类和包
	Ctrl+Enter，导入包，自动修正
	Ctrl+Alt+T，可以把代码包在一个块内，例如：try/catch
	Ctrl+Alt+V，可以引入变量。例如：new String();  自动导入变量定义
	Alt+Insert，可以生成构造器/Getter/Setter等
	
4 编辑

	Ctrl+W，可以选择单词继而语句继而行继而函数
	Ctrl+Shift+W，取消选择光标所在词
	Ctrl+Left/Right，移动光标到前/后单词
	Ctrl+[/]，移动到前/后代码块
	Ctrl+Left/Right/[]加上Shift，能选中跳跃范围内的代码。

	Ctrl+"+/-"，当前折叠代码
	Ctrl+Shift+"+/-"，全部展开、折叠
	Ctrl+Shift+J，整合两行
	Ctrl+G，定位行
	Ctrl+X，剪切
	Ctrl+R，替换文本
	Ctrl+Y，删除行
	Ctrl+D，复制行
	Ctrl+Backspace，按单词删除
	Ctrl+Enter，上插一行
	Shift+Enter，向下插入新行
	Ctrl+Shift+Up/Down，向上/下移动语句
	Alt+Shift+Up/Down，上/下移一行
	Ctrl+Up/Down，光标中转到第一行或最后一行下

	Ctrl+Alt+L，格式化代码
	Ctrl+I，实现方法
	Ctrl+Shift+U，大小写转化
	Ctrl+Shift+V，打开剪贴板内容，选择插入项
	
5 查找打开

	Ctrl+N/Ctrl+Shift+N，可以打开类或资源
	Shift+Shift，可在一个弹出框中搜索任何东西，包括类、资源、配置项、方法等等


	Ctrl+H，类的继承关系窗口

	在继承层次上跳转则用Ctrl+B/Ctrl+Alt+B分别对应父类或父方法定义和子类或子方法实现

	查看当前类的所有方法用Ctrl+F12。

	Alt+Q，可以看到当前方法的声明
	Ctrl+P，可以显示参数信息
	Ctrl+Shift+N，可以快速打开文件

	Ctrl+H，显示类结构图（类的继承层次）
	Ctrl+Q，显示注释文档
	Alt+F1，查找代码所在位置
	Alt+1，快速打开或隐藏工程面板
	Ctrl+Alt+S，打开设置对话框
	Ctrl+Alt+left/right，返回至上次浏览的位置
	Alt+left/right，切换代码视图

	Ctrl+Alt+Shift+S，打开当前项目/模块属性
	Alt+Shift+Inert，开启/关闭列选择模式
	Alt+Up/Down，在方法间快速移动定位
	F2 或 Shift+F2，高亮错误或警告快速定位

	Ctrl+B/Ctrl+Click，快速打开光标处的类或方法（跳转到定义处）
	Ctrl+Alt+B，跳转到方法实现处
	Ctrl+Shift+Backspace，跳转到上次编辑的地方

	Alt+F7，查找整个工程中使用地某一个类、方法或者变量的位置
	Ctrl+F，查找/Shift+F3，向上查找/F3，向下查找
	Ctrl+Shift+F，全局查找，配合F3/Shift+F3前后移动到下一匹配处。
	Ctrl+Shift+S，高级搜索
	Ctrl+U，转到父类
	Shift+F1，要打开编辑器光标字符处使用的类或者方法 Java 文档的浏览器

	Alt+F3，逐个往下查找相同文本，并高亮显示
	Ctrl+Shift+F7，高亮显示所有该文本，按 Esc 高亮消失

	Ctrl+Alt+Up/Down，快速跳转搜索结果
	Ctrl+Shift+Alt+N，查找类中的方法或变量
	ctrl+F12，类方法列表
	ctrl+shift+H，查看类或方法被调用情况
	
6 其他辅助

	以上这些神键配上一些辅助快捷键，即可让你的双手90%以上的时间摆脱鼠标，专注于键盘仿佛在进行钢琴表演。这些不起眼却是至关重要的最后一块拼图有：
	命令：Ctrl+Shift+A可以查找所有Intellij的命令，并且每个命令后面还有其快捷键。所以它不仅是一大神键，也是查找学习快捷键的工具。
	新建：Alt+Insert可以新建类、方法等任何东西。
	格式化代码：格式化import列表Ctrl+Alt+O，格式化代码Ctrl+Alt+L。
	切换窗口：Alt+Num，常用的有1-项目结构，3-搜索结果，4/5-运行调试。Ctrl+Tab切换标签页，Ctrl+E/Ctrl+Shift+E打开最近打开过的或编辑过的文件。
	单元测试：Ctrl+Alt+T创建单元测试用例。
	运行：Alt+Shift+F10运行程序，Shift+F9启动调试，Ctrl+F2停止。
	调试：F7/F8/F9分别对应Step into，Step over，Continue。
	此外还有些我自定义的，例如水平分屏Ctrl+|等，和一些神奇的小功能Ctrl+Shift+V粘贴很早以前拷贝过的，Alt+Shift+Insert进入到列模式进行按列选中。
	
7 调试部分、编译

	1. Ctrl+F2，停止
	2. Alt+Shift+F9，选择 Debug
	3. Alt+Shift+F10，选择 Run
	4. Ctrl+Shift+F9，编译
	5. Ctrl+Shift+F10，运行
	6. Ctrl+Shift+F8，查看断点
	7. F8，步过
	8. F7，步入
	9. Shift+F7，智能步入
	10. Shift+F8，步出
	11. Alt+Shift+F8，强制步过
	12. Alt+Shift+F7，强制步入
	13. Alt+F9，运行至光标处
	14. Ctrl+Alt+F9，强制运行至光标处
	15. F9，恢复程序
	16. Alt+F10，定位到断点
	17. Ctrl+F8，切换行断点
	18. Ctrl+F9，生成项目
	19. Alt+1，项目
	20. Alt+2，收藏
	21. Alt+6，TODO
	22. Alt+7，结构
	23. Ctrl+Shift+C，复制路径
	24. Ctrl+Alt+Shift+C，复制引用，必须选择类名
	25. Ctrl+Alt+Y，同步
	26. Ctrl+~，快速切换方案（界面外观、代码风格、快捷键映射等菜单）
	27. Shift+F12，还原默认布局
	28. Ctrl+Shift+F12，隐藏/恢复所有窗口
	29. Ctrl+F4，关闭
	30. Ctrl+Shift+F4，关闭活动选项卡
	31. Ctrl+Tab，转到下一个拆分器
	32. Ctrl+Shift+Tab，转到上一个拆分器
	
8 最终榜单

	Top #10切来切去：Ctrl+Tab
	Top #9选你所想：Ctrl+W
	Top #8代码生成：Template/Postfix +Tab
	Top #7发号施令：Ctrl+Shift+A
	Top #6无处藏身：Shift+Shift
	Top #5自动完成：Ctrl+Shift+Enter
	Top #4创造万物：Alt+Insert！
	Top #1智能补全：Ctrl+Shift+Space
	Top #1自我修复：Alt+Enter
	Top #1重构一切：Ctrl+Shift+Alt+T
	
3 其他

	主题：File > Settings > Apperence > theme里选择 Darcula，我的是V12
	编辑器背景：File > Settings > Editor > Colors & Fonts > General > Added lines in gutter > Background R:201, G:222, B:193
	开始单元测试：ctrl+shift+F10
	properties中文：File/settings/editor/file encodings/Transparent native-to-ascii conversion选项划勾
	注释模板：File/settings/editor/File and Code Templates/Templates，选中某类型以后，将#parse("File Header.java")这一行删除。替换为你自己的注释，例如：
	#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
	/**
	 * ${NAME}
	 * 
	 * @author nouuid
	 * @date ${DATE}
	 */
	public @interface ${NAME} {
	}
	includes选项卡/File Header：
	/**
	 * @author ${USER}
	 * @date ${DATE}
	 * @description 
	 */
	
导出设置

	file -> export setting

IDEA14:

	17118-YS3SI-JQU56-AT4PR-XN66K-3PKK0

IDEA16 license for lanyu:

	43B4A73YYJ-eyJsaWNlbnNlSWQiOiI0M0I0QTczWVlKIiwibGljZW5zZWVOYW1lIjoibGFuIHl1IiwiYXNzaWduZWVOYW1lIjoiIiwiYXNzaWduZWVFbWFpbCI6IiIsImxpY2Vuc2VSZXN0cmljdGlvbiI6IkZvciBlZHVjYXRpb25hbCB1c2Ugb25seSIsImNoZWNrQ29uY3VycmVudFVzZSI6ZmFsc2UsInByb2R1Y3RzIjpbeyJjb2RlIjoiSUkiLCJwYWlkVXBUbyI6IjIwMTctMDItMjUifSx7ImNvZGUiOiJBQyIsInBhaWRVcFRvIjoiMjAxNy0wMi0yNSJ9LHsiY29kZSI6IkRQTiIsInBhaWRVcFRvIjoiMjAxNy0wMi0yNSJ9LHsiY29kZSI6IlBTIiwicGFpZFVwVG8iOiIyMDE3LTAyLTI1In0seyJjb2RlIjoiRE0iLCJwYWlkVXBUbyI6IjIwMTctMDItMjUifSx7ImNvZGUiOiJDTCIsInBhaWRVcFRvIjoiMjAxNy0wMi0yNSJ9LHsiY29kZSI6IlJTMCIsInBhaWRVcFRvIjoiMjAxNy0wMi0yNSJ9LHsiY29kZSI6IlJDIiwicGFpZFVwVG8iOiIyMDE3LTAyLTI1In0seyJjb2RlIjoiUEMiLCJwYWlkVXBUbyI6IjIwMTctMDItMjUifSx7ImNvZGUiOiJSTSIsInBhaWRVcFRvIjoiMjAxNy0wMi0yNSJ9LHsiY29kZSI6IldTIiwicGFpZFVwVG8iOiIyMDE3LTAyLTI1In0seyJjb2RlIjoiREIiLCJwYWlkVXBUbyI6IjIwMTctMDItMjUifSx7ImNvZGUiOiJEQyIsInBhaWRVcFRvIjoiMjAxNy0wMi0yNSJ9XSwiaGFzaCI6IjMzOTgyOTkvMCIsImdyYWNlUGVyaW9kRGF5cyI6MCwiYXV0b1Byb2xvbmdhdGVkIjpmYWxzZSwiaXNBdXRvUHJvbG9uZ2F0ZWQiOmZhbHNlfQ==-keaxIkRgXPKE4BR/ZTs7s7UkP92LBxRe57HvWamu1EHVXTcV1B4f/KNQIrpOpN6dgpjig5eMVMPmo7yMPl+bmwQ8pTZaCGFuLqCHD1ngo6ywHKIQy0nR249sAUVaCl2wGJwaO4JeOh1opUx8chzSBVRZBMz0/MGyygi7duYAff9JQqfH3p/BhDTNM8eKl6z5tnneZ8ZG5bG1XvqFTqWk4FhGsEWdK7B+He44hPjBxKQl2gmZAodb6g9YxfTHhVRKQY5hQ7KPXNvh3ikerHkoaL5apgsVBZJOTDE2KdYTnGLmqxghFx6L0ofqKI6hMr48ergMyflDk6wLNGWJvYHLWw==-MIIEPjCCAiagAwIBAgIBBTANBgkqhkiG9w0BAQsFADAYMRYwFAYDVQQDDA1KZXRQcm9maWxlIENBMB4XDTE1MTEwMjA4MjE0OFoXDTE4MTEwMTA4MjE0OFowETEPMA0GA1UEAwwGcHJvZDN5MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxcQkq+zdxlR2mmRYBPzGbUNdMN6OaXiXzxIWtMEkrJMO/5oUfQJbLLuMSMK0QHFmaI37WShyxZcfRCidwXjot4zmNBKnlyHodDij/78TmVqFl8nOeD5+07B8VEaIu7c3E1N+e1doC6wht4I4+IEmtsPAdoaj5WCQVQbrI8KeT8M9VcBIWX7fD0fhexfg3ZRt0xqwMcXGNp3DdJHiO0rCdU+Itv7EmtnSVq9jBG1usMSFvMowR25mju2JcPFp1+I4ZI+FqgR8gyG8oiNDyNEoAbsR3lOpI7grUYSvkB/xVy/VoklPCK2h0f0GJxFjnye8NT1PAywoyl7RmiAVRE/EKwIDAQABo4GZMIGWMAkGA1UdEwQCMAAwHQYDVR0OBBYEFGEpG9oZGcfLMGNBkY7SgHiMGgTcMEgGA1UdIwRBMD+AFKOetkhnQhI2Qb1t4Lm0oFKLl/GzoRykGjAYMRYwFAYDVQQDDA1KZXRQcm9maWxlIENBggkA0myxg7KDeeEwEwYDVR0lBAwwCgYIKwYBBQUHAwEwCwYDVR0PBAQDAgWgMA0GCSqGSIb3DQEBCwUAA4ICAQC9WZuYgQedSuOc5TOUSrRigMw4/+wuC5EtZBfvdl4HT/8vzMW/oUlIP4YCvA0XKyBaCJ2iX+ZCDKoPfiYXiaSiH+HxAPV6J79vvouxKrWg2XV6ShFtPLP+0gPdGq3x9R3+kJbmAm8w+FOdlWqAfJrLvpzMGNeDU14YGXiZ9bVzmIQbwrBA+c/F4tlK/DV07dsNExihqFoibnqDiVNTGombaU2dDup2gwKdL81ua8EIcGNExHe82kjF4zwfadHk3bQVvbfdAwxcDy4xBjs3L4raPLU3yenSzr/OEur1+jfOxnQSmEcMXKXgrAQ9U55gwjcOFKrgOxEdek/Sk1VfOjvS+nuM4eyEruFMfaZHzoQiuw4IqgGc45ohFH0UUyjYcuFxxDSU9lMCv8qdHKm+wnPRb0l9l5vXsCBDuhAGYD6ss+Ga+aDY6f/qXZuUCEUOH3QUNbbCUlviSz6+GiRnt1kA9N2Qachl+2yBfaqUqr8h7Z2gsx5LcIf5kYNsqJ0GavXTVyWh7PYiKX4bs354ZQLUwwa/cG++2+wNWP+HtBhVxMRNTdVhSm38AknZlD+PTAsWGu9GyLmhti2EnVwGybSD2Dxmhxk3IPCkhKAK+pl0eWYGZWG3tJ9mZ7SowcXLWDFAk0lRJnKGFMTggrWjV8GYpw5bq23VmIqqDLgkNzuoog==