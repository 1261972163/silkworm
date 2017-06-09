Sublime

	# 1 安装sublime-evernote

	## 1.1 用Sublime的PackageControl安装Evernote
	## 1.2 设置Sublime与印象笔记做关联

	* 国内用户打开 https://app.yinxiang.com/api/DeveloperToken.action
	* 国际用户打开 https://www.evernote.com/api/DeveloperToken.action
	* 点击点击 Create a developer token。
	* 打开 Preferences > Package Settings > Evernote >Settings - User
	* 在文件中贴入如下内容

			{
			 "noteStoreUrl": "",
			 "token": ""
			}

		noteStoreUrl和token值为之前打开的页面的上的值。保存。

	* 测试是否成功：通过shift+command+p打开命令窗口,输入Evernote，就会看见Evernote的许多命令,点击Evernote:list recent notes,如果看到罗列出最新的笔记，则说明授权成功

Sublime快捷键

	ctrl+shift+d 复制当前行到下一行