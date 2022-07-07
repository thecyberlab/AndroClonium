.class public LActivityTest0;
.super Landroid/app/Activity;
.source "ActivityTest0.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 13
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    return-void
.end method


# virtual methods
.method public onCreate(Landroid/os/Bundle;)V
    .locals 1
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "savedInstanceState"
        }
    .end annotation

    .line 17
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 18
    const v0, 0x7f09001d

    invoke-virtual {p0, v0}, LActivityTest0;->setContentView(I)V

    .line 19
    return-void
.end method

.method public test1()Ljava/io/File;
    .locals 1

    .line 22
    invoke-virtual {p0}, LActivityTest0;->getFilesDir()Ljava/io/File;

    move-result-object v0

    return-object v0
.end method

.method public test2(Ljava/lang/String;)Ljava/io/File;
    .locals 1
    .param p1, "type"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "type"
        }
    .end annotation

    .line 26
    invoke-virtual {p0, p1}, LActivityTest0;->getExternalFilesDir(Ljava/lang/String;)Ljava/io/File;

    move-result-object v0

    return-object v0
.end method

.method public test3()Ljava/io/File;
    .locals 1

    .line 30
    invoke-virtual {p0}, LActivityTest0;->getCacheDir()Ljava/io/File;

    move-result-object v0

    return-object v0
.end method

.method public test4()Ljava/io/File;
    .locals 1

    .line 34
    invoke-virtual {p0}, LActivityTest0;->getExternalCacheDir()Ljava/io/File;

    move-result-object v0

    return-object v0
.end method

.method public test5()[Ljava/io/File;
    .locals 1

    .line 39
    invoke-virtual {p0}, LActivityTest0;->getExternalMediaDirs()[Ljava/io/File;

    move-result-object v0

    return-object v0
.end method

.method public test6(Ljava/lang/String;)[Ljava/io/File;
    .locals 1
    .param p1, "type"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "type"
        }
    .end annotation

    .line 44
    invoke-virtual {p0, p1}, LActivityTest0;->getExternalFilesDirs(Ljava/lang/String;)[Ljava/io/File;

    move-result-object v0

    return-object v0
.end method

.method public test7()Landroid/content/res/Resources;
    .locals 1

    .line 48
    invoke-virtual {p0}, LActivityTest0;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    return-object v0
.end method

.method public test8()Ljava/lang/String;
    .locals 2

    .line 52
    invoke-virtual {p0}, LActivityTest0;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f0b002a

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public test9()Ljava/lang/String;
    .locals 1

    .line 56
    const v0, 0x7f0b002a

    invoke-virtual {p0, v0}, LActivityTest0;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public test10()Ljava/lang/String;
    .locals 2

    .line 52
    invoke-virtual {p0}, LActivityTest0;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f0b032a

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public test11()Ljava/lang/String;
    .locals 1

    .line 56
    const v0, 0x7f04302a

    invoke-virtual {p0, v0}, LActivityTest0;->getString(I)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method