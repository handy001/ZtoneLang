## Command line instructions

### Create a new repository

```
git clone https://code.aliyun.com/ztone/ZtoneLang.git
cd ZtoneLang
touch README.md
git add README.md
git commit -m "add README"
git push -u origin master
```

### Existing folder or Git repositorygit

```
cd existing_folder
git init
git remote add origin https://code.aliyun.com/ztone/ZtoneLang.git
git add .
git commit
git push -u origin master
```

### Maven

```
./gradlew -q -p ztone-lang clean build bintrayUpload
```
