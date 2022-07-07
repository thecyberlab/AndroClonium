.class public Lstarcom/snd/WebRadioChannel;
.super Ljava/lang/Object;
.source "WebRadioChannel.java"

# interfaces
.implements Ljava/lang/Comparable;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Ljava/lang/Comparable",
        "<",
        "Lstarcom/snd/WebRadioChannel;",
        ">;"
    }
.end annotation


# instance fields
.field private radioName:Ljava/lang/String;

.field private radioUrl:Ljava/lang/String;

.field private selected:Z


# direct methods
.method public constructor <init>(Ljava/lang/String;Ljava/lang/String;)V
    .locals 1
    .param p1, "radioName"    # Ljava/lang/String;
    .param p2, "radioUrl"    # Ljava/lang/String;

    .prologue
    .line 11
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 8
    const/4 v0, 0x1

    iput-boolean v0, p0, Lstarcom/snd/WebRadioChannel;->selected:Z

    .line 12
    iput-object p1, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    .line 13
    iput-object p2, p0, Lstarcom/snd/WebRadioChannel;->radioUrl:Ljava/lang/String;

    .line 14
    return-void
.end method


# virtual methods
.method public bridge synthetic compareTo(Ljava/lang/Object;)I
    .locals 5

    .prologue
    .line 3
    check-cast p1, Lstarcom/snd/WebRadioChannel;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v3, 0x0

    aput-object p1, v1, v3

    const/16 v3, 0xe

    invoke-static {v3, p0, v1}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    return v0
.end method

.method public compareTo(Lstarcom/snd/WebRadioChannel;)I
    .locals 4
    .param p1, "other"    # Lstarcom/snd/WebRadioChannel;

    .prologue
    .line 58
    instance-of v2, p1, Lstarcom/snd/WebRadioChannel;

    if-eqz v2, :cond_1

    .line 60
    move-object v1, p1

    .line 61
    .local v1, "otherW":Lstarcom/snd/WebRadioChannel;
    iget-object v2, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    iget-object v3, v1, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/String;->compareTo(Ljava/lang/String;)I

    move-result v0

    .line 62
    .local v0, "cmp":I
    if-eqz v0, :cond_0

    .line 65
    .end local v0    # "cmp":I
    .end local v1    # "otherW":Lstarcom/snd/WebRadioChannel;
    :goto_0
    return v0

    .line 63
    .restart local v0    # "cmp":I
    .restart local v1    # "otherW":Lstarcom/snd/WebRadioChannel;
    :cond_0
    iget-object v2, p0, Lstarcom/snd/WebRadioChannel;->radioUrl:Ljava/lang/String;

    iget-object v3, v1, Lstarcom/snd/WebRadioChannel;->radioUrl:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/String;->compareTo(Ljava/lang/String;)I

    move-result v0

    goto :goto_0

    .line 65
    .end local v0    # "cmp":I
    .end local v1    # "otherW":Lstarcom/snd/WebRadioChannel;
    :cond_1
    const/4 v0, 0x1

    goto :goto_0
.end method

.method public equals(Ljava/lang/Object;)Z
    .locals 7
    .param p1, "other"    # Ljava/lang/Object;

    .prologue
    const/4 v1, 0x0

    .line 71
    instance-of v2, p1, Lstarcom/snd/WebRadioChannel;

    if-eqz v2, :cond_0

    move-object v0, p1

    .line 73
    check-cast v0, Lstarcom/snd/WebRadioChannel;

    .line 74
    .local v0, "otherW":Lstarcom/snd/WebRadioChannel;
    const/4 v3, 0x1

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v5, 0x0

    aput-object v0, v3, v5

    const/16 v5, 0xf

    invoke-static {v5, p0, v3}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Integer;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v2

    if-nez v2, :cond_0

    const/4 v1, 0x1

    .line 76
    .end local v0    # "otherW":Lstarcom/snd/WebRadioChannel;
    :cond_0
    return v1
.end method

.method public getFullName()Ljava/lang/String;
    .locals 1

    .prologue
    .line 35
    iget-object v0, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    return-object v0
.end method

.method public getGenreIcon()I
    .locals 3

    .prologue
    const v0, 0x7f010005

    .line 18
    iget-object v1, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    const-string/jumbo v2, "[e] "

    invoke-virtual {v1, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_1

    const v0, 0x7f010001

    .line 24
    :cond_0
    :goto_0
    return v0

    .line 19
    :cond_1
    iget-object v1, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    const-string/jumbo v2, "[r] "

    invoke-virtual {v1, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_2

    const v0, 0x7f010004

    goto :goto_0

    .line 20
    :cond_2
    iget-object v1, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    const-string/jumbo v2, "[o] "

    invoke-virtual {v1, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_3

    const v0, 0x7f010003

    goto :goto_0

    .line 21
    :cond_3
    iget-object v1, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    const-string/jumbo v2, "[c] "

    invoke-virtual {v1, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_4

    const/high16 v0, 0x7f010000

    goto :goto_0

    .line 22
    :cond_4
    iget-object v1, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    const-string/jumbo v2, "[j] "

    invoke-virtual {v1, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_5

    const v0, 0x7f010002

    goto :goto_0

    .line 23
    :cond_5
    iget-object v1, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    const-string/jumbo v2, "[u] "

    invoke-virtual {v1, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_0

    goto :goto_0
.end method

.method public getName()Ljava/lang/String;
    .locals 6

    .prologue
    .line 29
    const/4 v2, 0x0

    const/16 v4, 0x10

    invoke-static {v4, p0, v2}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    const v1, 0x7f010005

    if-ne v0, v1, :cond_0

    iget-object v0, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    const-string/jumbo v1, "[u] "

    invoke-virtual {v0, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    .line 30
    :goto_0
    return-object v0

    :cond_0
    iget-object v0, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    const/4 v1, 0x4

    invoke-virtual {v0, v1}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v0

    goto :goto_0
.end method

.method public getUrl()Ljava/lang/String;
    .locals 1

    .prologue
    .line 40
    iget-object v0, p0, Lstarcom/snd/WebRadioChannel;->radioUrl:Ljava/lang/String;

    return-object v0
.end method

.method public isSelected()Z
    .locals 1

    .prologue
    .line 46
    iget-boolean v0, p0, Lstarcom/snd/WebRadioChannel;->selected:Z

    return v0
.end method

.method public setData(Lstarcom/snd/WebRadioChannel;)V
    .locals 1
    .param p1, "newChannel"    # Lstarcom/snd/WebRadioChannel;

    .prologue
    .line 83
    iget-object v0, p1, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    iput-object v0, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    .line 84
    iget-object v0, p1, Lstarcom/snd/WebRadioChannel;->radioUrl:Ljava/lang/String;

    iput-object v0, p0, Lstarcom/snd/WebRadioChannel;->radioUrl:Ljava/lang/String;

    .line 85
    iget-boolean v0, p1, Lstarcom/snd/WebRadioChannel;->selected:Z

    iput-boolean v0, p0, Lstarcom/snd/WebRadioChannel;->selected:Z

    .line 86
    return-void
.end method

.method public setSelected(Z)V
    .locals 0
    .param p1, "selected"    # Z

    .prologue
    .line 52
    iput-boolean p1, p0, Lstarcom/snd/WebRadioChannel;->selected:Z

    .line 53
    return-void
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    .prologue
    .line 79
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lstarcom/snd/WebRadioChannel;->radioName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string/jumbo v1, "\n"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lstarcom/snd/WebRadioChannel;->radioUrl:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
