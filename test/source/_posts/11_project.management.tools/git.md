
git使用笔记
===============

[Reference Manual](https://git-scm.com/docs)
[Pro Git](https://git-scm.com/book/en/v2)

# 1. 本地配置

	查看git配置
	git config --list

	配置适用范围
	1) git config 针对一个git仓库
	2) git config --global    针对一个用户
	3) sudo git config --system    针对一个系统，因为是针对整个系统的，所以必须使用sudo

	修改提交用户
	git config --global user.email "youremail@google.com"
	git config --global user.name "your name"

# 2. 缓存区

	回退add
	git reset HEAD <file>

# 3. 工作区

# 4. 仓库