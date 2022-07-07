.class public LTestInterfaceWithDefaultMethods;
.super Ljava/lang/Object;

.implements LInterfaceWithDefaultMethods;

.method constructor <init>()V
    .locals 0

    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


.method public test()Ljava/lang/String;
    .locals 1

    .line 6
    invoke-super {p0}, LInterfaceWithDefaultMethods;->doStuff()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

