Eclipse设置

	1 关闭验证

	（1）菜单【Windows】 -【Preperences】-【Validation】 
	（2）在右边找到“XML Validator”，把取消【Bulid】复选框的选中状态，保持【Manual】 的选中状态，因为我们最后还需要确保XML的正确性，这个可以通过选择XML文件，在右键菜单中选择【Validate】手工来验证，如果XML有错，会弹出窗口提示，但如果把【Manual】复选框也取消的话，再手工验证，即使XML真有错，也不会正确提示。
	
	2 设置字体

	（1）Window/Preferences/General/Appearance/Colors and Fonts/Basic/Text Font （2）推荐选择：字体Consolas，大小12。 
	（3）如果没有Consolas，选择显示"显示更多字体"，将Consolas设置为“显示”。 

	3 代码视图

	Window/show View/Package Explorer 

	4 文档打开方式

	（1）Windows/Preferences/General/Editors/File Associations 
	（2）File types中选择文件类型，Associated editors中选择Editor，点击Default 

	5 Maven设置

	（1）Window/Preferences/Maven/User Sttings （2）设置settings.xml的路径 

	6 java文件格式化设置

	（1）Window/Preferences/java/code style/formatter 
	（2）点击Edit，新建my-formatter，选择lineWrapping，设置Maximum line width等项
	（3）Comments选项卡里把General settings的第三项Enable line comment formatting取消勾选 

	7 取消注释斜体

	Window/Preferences/java/Editor/Syntax coloring/Element中所有Italic的勾取消 

	8 Package Explorer与java编辑器关联

	Package Explorer栏，有个倒三角，选中，选择Link with Editor 

	9 修改Package Presentation

	Package Explorer栏，有个倒三角，选中，选中Package Presentation，选择Hierarchical 

	10 注释模板

	（1）Window/Preference/Java/Code Style/Code Template/Comments 
	（2）文件(Files)注释标签： 

		/**
		 *
		 * @Title: ${file_name}
		 * @Package ${package_name}
		 * @Description: ${todo}(用一句话描述该文件做什么)
		 * @author A18ccms A18ccms_gmail_com
		 * @date ${date} ${time}
		 * @version V1.0
		 * / 

	（3）方法(Constructor & Methods)标签： 
		/**
		 * @Title: ${enclosing_method}
		 * @Description: ${todo}
		 * ${tags}
		 */ 

	11 内存设置

	（1）配置方法： eclipse文件下有个eclipse.ini文件，配置如： -Xmx1024m -Xms512m –XX:PermSize=64m -XX:MaxPermSize=256m -XX:PermSize：最小堆大小。一般报内存不足时,都是说这个太小，堆空间剩余小于5%就会警告,建议把这个稍微设大一点，不过要视自己机器内存大小来设置，但不能超过MaxPermSize。 
	（2）寻找eclipse.ini文件： 点击“开始”->搜索：eclipse.ini 

	12 zookeeper插件

	插件地址：http://www.massedynamic.org/eclipse/updates/ 选择“Plug-in for ZooKeeper3.2.2” 13 maven插件

	14 修改Eclipse格式化代默认长度

	Java代码 
		打开Eclipse的Window菜单，然后Preferences->Java->Code Style->Formatter->Edit ->Line Wrapping->Maximum line width:默认80（sun推荐）加个0，改成800就行了。 
	CSS代码 
		Window->Preferences->Web->CSS Files->Editor->Line width:默认为72，加个0，改为720保存。 
	Html代码 
		Window->Preferences->Web->HTML Files->Editor->Line width:默认为72，加个0，改为720保存。 
	JSP代码 
		Window->Preferences->Web->JSP Files->Editor->Line width:默认为72，加个0，改为720保存。 
	xml代码 
		XML->XML Files->Editor->Line width:默认为72，加个0，改为720保存。 

	15 编码格式

	Window -> Preferences -> General -> Workspace -> Text file encoding 

	16 设置tab键为4个空格

	点击 window->preference-,依次选择 General->Editors->Text Editors,选中右侧的 insert space for tabs；保存； 
	点击 window->preference-,依次选择 java（或C++）->code style ->formatter,点击右侧的editor，选则左侧 tab policy的值为spaces only,确定，应用保存即可。

eclipse快捷键

	通过Eclipse快捷键可以更加容易的浏览源代码，使得整体的开发效率和质量得到提升。一个Eclipse骨灰级开发者总结了他认为最有用但又不太为人所知的Eclipse中10个最有用的快捷键组合：

	1. ctrl+shift+r：打开资源

	这可能是所有快捷键组合中最省时间的了。这组快捷键可以让你打开你的工作区中任何一个文件，而你只需要按下文件名或mask名中的前几个字母，比如applic*.xml。美中不足的是这组快捷键并非在所有视图下都能用。
	2. ctrl+o：快速outline

	如果想要查看当前类的方法或某个特定方法，但又不想把代码拉上拉下，也不想使用查找功能的话，就用ctrl+o吧。它可以列出当前类中的所有方法及属性，你只需输入你想要查询的方法名，点击enter就能够直接跳转至你想去的位置。
	3. ctrl+e：快速转换编辑器

	这组快捷键将帮助你在打开的编辑器之间浏览。使用ctrl+page down或ctrl+page up可以浏览前后的选项卡，但是在很多文件打开的状态下，ctrl+e会更加有效率。
	4. ctrl+2，L：为本地变量赋值

	开发过程中，我常常先编写方法，如Calendar.getInstance()，然后通过ctrl+2快捷键将方法的计算结果赋值于一个本地变量之上。 这样我节省了输入类名，变量名以及导入声明的时间。Ctrl+F的效果类似，不过效果是把方法的计算结果赋值于类中的域。
	5. alt+shift+r：重命名

	重命名属性及方法在几年前还是个很麻烦的事，需要大量使用搜索及替换，以至于代码变得零零散散的。今天的Java IDE提供源码处理功能，Eclipse也是一样。现在，变量和方法的重命名变得十分简单，你会习惯于在每次出现更好替代名称的时候都做一次重命名。要使 用这个功能，将鼠标移动至属性名或方法名上，按下alt+shift+r，输入新名称并点击回车。就此完成。如果你重命名的是类中的一个属性，你可以点击alt+shift+r两次，这会呼叫出源码处理对话框，可以实现get及set方法的自动重命名。
	6. alt+shift+l以及alt+shift+m：提取本地变量及方法

	源码处理还包括从大块的代码中提取变量和方法的功能。比如，要从一个string创建一个常量，那么就选定文本并按下alt+shift+l即可。如果同 一个string在同一类中的别处出现，它会被自动替换。方法提取也是个非常方便的功能。将大方法分解成较小的、充分定义的方法会极大的减少复杂度，并提 升代码的可测试性。
	7. shift+enter及ctrl+shift+enter

	Shift+enter在当前行之下创建一个空白行，与光标是否在行末无关。Ctrl+shift+enter则在当前行之前插入空白行。
	8. Alt+方向键

	这也是个节省时间的法宝。这个组合将当前行的内容往上或下移动。在try/catch部分，这个快捷方式尤其好使。
	9. ctrl+m

	大显示屏幕能够提高工作效率是大家都知道的。Ctrl+m是编辑器窗口最大化的快捷键。
	10. ctrl+.及ctrl+1：下一个错误及快速修改

	ctrl+.将光标移动至当前文件中的下一个报错处或警告处。这组快捷键我一般与ctrl+1一并使用，即修改建议的快捷键。新版Eclipse的修改建 议做的很不错，可以帮你解决很多问题，如方法中的缺失参数，throw/catch exception，未执行的方法等等。
	更多快捷键组合可在Eclipse按下ctrl+shift+L查看。

	参考文献：
	1.http://www.open-open.com/bbs/view/1320934157953/
	2.http://www.cnblogs.com/ksuifeng/archive/2010/04/24/IDE_Eclipse.html
	3.http://wenku.baidu.com/view/06a9841efc4ffe473368abc6.html