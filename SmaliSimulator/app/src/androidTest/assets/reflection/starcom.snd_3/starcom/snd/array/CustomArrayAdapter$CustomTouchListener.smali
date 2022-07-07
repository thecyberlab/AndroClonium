.class public Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;
.super Ljava/lang/Object;
.source "CustomArrayAdapter.java"

# interfaces
.implements Landroid/view/View$OnLongClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lstarcom/snd/array/CustomArrayAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "CustomTouchListener"
.end annotation


# instance fields
.field activity:Landroid/app/Activity;

.field callback:Lstarcom/snd/listener/CallbackListener;

.field index:I

.field isDefault:Z


# direct methods
.method public constructor <init>(ILandroid/app/Activity;Lstarcom/snd/listener/CallbackListener;)V
    .locals 5
    .param p1, "listViewIdx"    # I
    .param p2, "activity"    # Landroid/app/Activity;
    .param p3, "callback"    # Lstarcom/snd/listener/CallbackListener;

    .prologue
    .line 86
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 87
    const/4 v1, 0x0

    const/16 v3, 0x29

    const/4 v4, 0x0

    invoke-static {v3, v4, v1}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/array/ChannelList;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const/4 v4, 0x0

    aput-object v2, v1, v4

    const/16 v3, 0x2a

    invoke-static {v3, v0, v1}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Boolean;

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v0

    iput-boolean v0, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->isDefault:Z

    .line 88
    iput-object p3, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->callback:Lstarcom/snd/listener/CallbackListener;

    .line 89
    iput-object p2, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->activity:Landroid/app/Activity;

    .line 90
    const/4 v1, 0x0

    const/16 v3, 0x2b

    const/4 v4, 0x0

    invoke-static {v3, v4, v1}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/array/ChannelList;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const/4 v4, 0x0

    aput-object v2, v1, v4

    const/16 v3, 0x2c

    invoke-static {v3, v0, v1}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    iput v0, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->index:I

    .line 91
    return-void
.end method


# virtual methods
.method public onLongClick(Landroid/view/View;)Z
    .locals 11
    .param p1, "v"    # Landroid/view/View;

    .prologue
    .line 96
    iget v6, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->index:I

    .line 97
    .local v6, "selectedIdx":I
    iget-boolean v0, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->isDefault:Z

    if-eqz v0, :cond_0

    const/4 v6, -0x1

    .line 98
    :cond_0
    const/4 v7, 0x0

    const/16 v9, 0x2d

    const/4 v10, 0x0

    invoke-static {v9, v10, v7}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/array/ChannelList;

    iget-boolean v1, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->isDefault:Z

    const/4 v7, 0x2

    new-array v7, v7, [Ljava/lang/Object;

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    const/4 v10, 0x0

    aput-object v8, v7, v10

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v8

    const/4 v10, 0x1

    aput-object v8, v7, v10

    const/16 v9, 0x2e

    invoke-static {v9, v0, v7}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 99
    iget-boolean v0, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->isDefault:Z

    if-eqz v0, :cond_1

    .line 101
    iget-object v0, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->activity:Landroid/app/Activity;

    invoke-virtual {v0}, Landroid/app/Activity;->getFragmentManager()Landroid/app/FragmentManager;

    move-result-object v1

    const-string/jumbo v2, "fragment_settings"

    const-class v3, Lstarcom/snd/dialog/SettingsDialog;

    iget-object v4, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->callback:Lstarcom/snd/listener/CallbackListener;

    sget-object v5, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->DefaultChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    move-object v0, p1

    invoke-static/range {v0 .. v5}, Lstarcom/snd/dialog/SettingsDialog;->showSettings(Landroid/view/View;Landroid/app/FragmentManager;Ljava/lang/String;Ljava/lang/Class;Lstarcom/snd/listener/CallbackListener;Lstarcom/snd/dialog/SettingsDialog$SettingsType;)V

    .line 107
    :goto_0
    const/4 v0, 0x1

    return v0

    .line 105
    :cond_1
    iget-object v0, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->activity:Landroid/app/Activity;

    invoke-virtual {v0}, Landroid/app/Activity;->getFragmentManager()Landroid/app/FragmentManager;

    move-result-object v1

    const-string/jumbo v2, "fragment_settings"

    const-class v3, Lstarcom/snd/dialog/SettingsDialog;

    iget-object v4, p0, Lstarcom/snd/array/CustomArrayAdapter$CustomTouchListener;->callback:Lstarcom/snd/listener/CallbackListener;

    sget-object v5, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->CustomChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    move-object v0, p1

    invoke-static/range {v0 .. v5}, Lstarcom/snd/dialog/SettingsDialog;->showSettings(Landroid/view/View;Landroid/app/FragmentManager;Ljava/lang/String;Ljava/lang/Class;Lstarcom/snd/listener/CallbackListener;Lstarcom/snd/dialog/SettingsDialog$SettingsType;)V

    goto :goto_0
.end method
