# Java Cource 2013 Team A

## Summary

このリポジトリでは、Javaコース参加者のリポジトリをsubmoduleとして束ねています。

初回は以下のコマンドを実行してください。

```sh
$ git clone https://github.com/JavaCourse2013/A.git
$ cd A
$ git submodule update --init
```

更新する場合は以下のコマンドを実行してください。

```sh
$ git submodule foreach git pull origin master
```

解答状況は[list.md](list.md)にまとめられています。
list.mdは以下のコマンドで更新することができます。

```sh
./gradlew list
```

list.mdに解答状況を反映させるには以下のディレクトリ構造になっている必要があります。

```
<Your root>/
JPL
	chXX
		exXX_XX		<- この中にソースコード
		...
	...
GUI
	1_1				<- この中にソースコードおよびJAR
	...
Interpret			<- この中に16章課題
```


## Members
 * [watagashi78 (Hasegawa)](https://github.com/watagashi78)
 * [shrhdk (shiro)](https://github.com/shrhdk)
 * [mikan (kato)](https://github.com/mikan)
 * [ichihira (sugimoto)](https://github.com/ichihira)
 * [zDpxq6 (hatanaka)](https://github.com/zDpxq6)
 * [akeboshi (aruga)](https://github.com/akeboshi)
 * kamiya
