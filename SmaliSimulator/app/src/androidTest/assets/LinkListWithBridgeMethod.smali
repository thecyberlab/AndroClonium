.class public LLinkListWithBridgeMethod;
.super Ljava/util/LinkedList;
.source "LinkListWithBridgeMethod.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/util/LinkedList<",
        "Landroid/app/Activity;",
        ">;"
    }
.end annotation


# instance fields
.field private maxSize:I


# direct methods
.method public constructor <init>(I)V
    .locals 0
    .param p1, "maxSize"    # I
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "maxSize"
        }
    .end annotation

    .line 11
    invoke-direct {p0}, Ljava/util/LinkedList;-><init>()V

    .line 12
    iput p1, p0, LLinkListWithBridgeMethod;->maxSize:I

    .line 13
    return-void
.end method


# virtual methods
.method public add(Landroid/app/Activity;)Z
    .locals 2
    .param p1, "object"    # Landroid/app/Activity;
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x0
        }
        names = {
            "object"
        }
    .end annotation

    .line 17
    invoke-virtual {p0}, LLinkListWithBridgeMethod;->size()I

    move-result v0

    iget v1, p0, LLinkListWithBridgeMethod;->maxSize:I

    if-ne v0, v1, :cond_0

    .line 18
    invoke-virtual {p0}, LLinkListWithBridgeMethod;->removeFirst()Landroid/app/Activity;

    .line 19
    :cond_0
    invoke-super {p0, p1}, Ljava/util/LinkedList;->add(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method

.method public bridge synthetic add(Ljava/lang/Object;)Z
    .locals 0
    .annotation system Ldalvik/annotation/MethodParameters;
        accessFlags = {
            0x1000
        }
        names = {
            "object"
        }
    .end annotation

    .line 7
    check-cast p1, Landroid/app/Activity;

    invoke-virtual {p0, p1}, LLinkListWithBridgeMethod;->add(Landroid/app/Activity;)Z

    move-result p1

    return p1
.end method

.method public removeFirst()Landroid/app/Activity;
    .locals 1

    .line 24
    invoke-super {p0}, Ljava/util/LinkedList;->removeFirst()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/Activity;

    return-object v0
.end method

.method public bridge synthetic removeFirst()Ljava/lang/Object;
    .locals 1

    .line 7
    invoke-virtual {p0}, LLinkListWithBridgeMethod;->removeFirst()Landroid/app/Activity;

    move-result-object v0

    return-object v0
.end method

.method public removeLast()Landroid/app/Activity;
    .locals 1

    .line 30
    invoke-super {p0}, Ljava/util/LinkedList;->removeLast()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/Activity;

    return-object v0
.end method

.method public bridge synthetic removeLast()Ljava/lang/Object;
    .locals 1

    .line 7
    invoke-virtual {p0}, LLinkListWithBridgeMethod;->removeLast()Landroid/app/Activity;

    move-result-object v0

    return-object v0
.end method
