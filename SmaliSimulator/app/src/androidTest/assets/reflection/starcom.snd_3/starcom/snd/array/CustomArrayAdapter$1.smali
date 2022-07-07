.class Lstarcom/snd/array/CustomArrayAdapter$1;
.super Ljava/lang/Object;
.source "CustomArrayAdapter.java"

# interfaces
.implements Landroid/widget/CompoundButton$OnCheckedChangeListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lstarcom/snd/array/CustomArrayAdapter;->createCheckboxListener(I)Landroid/widget/CompoundButton$OnCheckedChangeListener;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lstarcom/snd/array/CustomArrayAdapter;

.field final synthetic val$pos:I


# direct methods
.method constructor <init>(Lstarcom/snd/array/CustomArrayAdapter;I)V
    .locals 0
    .param p1, "this$0"    # Lstarcom/snd/array/CustomArrayAdapter;

    .prologue
    .line 65
    iput-object p1, p0, Lstarcom/snd/array/CustomArrayAdapter$1;->this$0:Lstarcom/snd/array/CustomArrayAdapter;

    iput p2, p0, Lstarcom/snd/array/CustomArrayAdapter$1;->val$pos:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onCheckedChanged(Landroid/widget/CompoundButton;Z)V
    .locals 8
    .param p1, "buttonView"    # Landroid/widget/CompoundButton;
    .param p2, "bSel"    # Z

    .prologue
    .line 69
    const/4 v4, 0x0

    const/16 v6, 0x1f

    const/4 v7, 0x0

    invoke-static {v6, v7, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lstarcom/snd/array/ChannelList;

    iget v3, p0, Lstarcom/snd/array/CustomArrayAdapter$1;->val$pos:I

    const/4 v4, 0x1

    new-array v4, v4, [Ljava/lang/Object;

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    const/4 v7, 0x0

    aput-object v5, v4, v7

    const/16 v6, 0x20

    invoke-static {v6, v2, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Boolean;

    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v1

    .line 70
    .local v1, "bDef":Z
    const/4 v4, 0x0

    const/16 v6, 0x21

    const/4 v7, 0x0

    invoke-static {v6, v7, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lstarcom/snd/array/ChannelList;

    iget v3, p0, Lstarcom/snd/array/CustomArrayAdapter$1;->val$pos:I

    const/4 v4, 0x1

    new-array v4, v4, [Ljava/lang/Object;

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    const/4 v7, 0x0

    aput-object v5, v4, v7

    const/16 v6, 0x22

    invoke-static {v6, v2, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    .line 71
    .local v0, "aPos":I
    if-eqz v1, :cond_0

    const/4 v4, 0x0

    const/16 v6, 0x23

    const/4 v7, 0x0

    invoke-static {v6, v7, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lstarcom/snd/array/ChannelList;

    const/4 v4, 0x0

    const/16 v6, 0x24

    invoke-static {v6, v2, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/ArrayList;

    invoke-virtual {v2, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lstarcom/snd/WebRadioChannel;

    const/4 v4, 0x1

    new-array v4, v4, [Ljava/lang/Object;

    invoke-static {p2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v5

    const/4 v7, 0x0

    aput-object v5, v4, v7

    const/16 v6, 0x25

    invoke-static {v6, v2, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 73
    :goto_0
    return-void

    .line 72
    :cond_0
    const/4 v4, 0x0

    const/16 v6, 0x26

    const/4 v7, 0x0

    invoke-static {v6, v7, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lstarcom/snd/array/ChannelList;

    const/4 v4, 0x0

    const/16 v6, 0x27

    invoke-static {v6, v2, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/ArrayList;

    invoke-virtual {v2, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lstarcom/snd/WebRadioChannel;

    const/4 v4, 0x1

    new-array v4, v4, [Ljava/lang/Object;

    invoke-static {p2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v5

    const/4 v7, 0x0

    aput-object v5, v4, v7

    const/16 v6, 0x28

    invoke-static {v6, v2, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0
.end method
