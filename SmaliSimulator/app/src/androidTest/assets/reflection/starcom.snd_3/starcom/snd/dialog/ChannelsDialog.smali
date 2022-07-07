.class public Lstarcom/snd/dialog/ChannelsDialog;
.super Lstarcom/snd/listener/DialogFragmentWithListener;
.source "ChannelsDialog.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# instance fields
.field listView:Landroid/widget/ListView;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 26
    invoke-direct {p0}, Lstarcom/snd/listener/DialogFragmentWithListener;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .param p1, "v"    # Landroid/view/View;

    .prologue
    .line 51
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const v1, 0x7f020014

    if-ne v0, v1, :cond_0

    .line 53
    const-class v0, Lstarcom/snd/dialog/ChannelsDialog;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    const-string/jumbo v3, "Selected: selectChannelsOk"

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 54
    invoke-virtual {p0}, Lstarcom/snd/dialog/ChannelsDialog;->dismiss()V

    .line 56
    :cond_0
    return-void
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 11
    .param p1, "inflater"    # Landroid/view/LayoutInflater;
    .param p2, "container"    # Landroid/view/ViewGroup;
    .param p3, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 31
    const v5, 0x7f030001

    invoke-virtual {p1, v5, p2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v4

    .line 32
    .local v4, "view":Landroid/view/View;
    const v5, 0x7f020014

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/Button;

    .line 33
    .local v3, "okButton":Landroid/widget/Button;
    invoke-virtual {v3, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 34
    invoke-virtual {p0}, Lstarcom/snd/dialog/ChannelsDialog;->getDialog()Landroid/app/Dialog;

    move-result-object v5

    const v6, 0x7f05000d

    invoke-virtual {v5, v6}, Landroid/app/Dialog;->setTitle(I)V

    .line 36
    const/4 v7, 0x0

    const/16 v9, 0x34

    const/4 v10, 0x0

    invoke-static {v9, v10, v7}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lstarcom/snd/array/ChannelList;

    const/4 v7, 0x0

    const/16 v9, 0x35

    invoke-static {v9, v5, v7}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/ArrayList;

    .line 37
    .local v1, "channelList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    const v5, 0x7f020007

    invoke-virtual {v4, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v5

    check-cast v5, Landroid/widget/ListView;

    iput-object v5, p0, Lstarcom/snd/dialog/ChannelsDialog;->listView:Landroid/widget/ListView;

    .line 38
    iget-object v5, p0, Lstarcom/snd/dialog/ChannelsDialog;->listView:Landroid/widget/ListView;

    const/4 v6, 0x2

    invoke-virtual {v5, v6}, Landroid/widget/ListView;->setChoiceMode(I)V

    .line 39
    new-instance v0, Lstarcom/snd/array/CustomArrayAdapter;

    invoke-virtual {p0}, Lstarcom/snd/dialog/ChannelsDialog;->getActivity()Landroid/app/Activity;

    move-result-object v5

    invoke-direct {v0, v5}, Lstarcom/snd/array/CustomArrayAdapter;-><init>(Landroid/app/Activity;)V

    .line 40
    .local v0, "adapter":Lstarcom/snd/array/CustomArrayAdapter;
    invoke-virtual {v1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v5

    :goto_0
    invoke-interface {v5}, Ljava/util/Iterator;->hasNext()Z

    move-result v6

    if-eqz v6, :cond_0

    invoke-interface {v5}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lstarcom/snd/WebRadioChannel;

    .line 42
    .local v2, "curChannel":Lstarcom/snd/WebRadioChannel;
    invoke-virtual {v0, v2}, Lstarcom/snd/array/CustomArrayAdapter;->add(Ljava/lang/Object;)V

    goto :goto_0

    .line 44
    .end local v2    # "curChannel":Lstarcom/snd/WebRadioChannel;
    :cond_0
    iget-object v5, p0, Lstarcom/snd/dialog/ChannelsDialog;->listView:Landroid/widget/ListView;

    invoke-virtual {v5, v0}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 45
    return-object v4
.end method

.method public onDismiss(Landroid/content/DialogInterface;)V
    .locals 7
    .param p1, "di"    # Landroid/content/DialogInterface;

    .prologue
    .line 61
    invoke-super {p0, p1}, Lstarcom/snd/listener/DialogFragmentWithListener;->onDismiss(Landroid/content/DialogInterface;)V

    .line 62
    const/4 v3, 0x0

    const/16 v5, 0x36

    const/4 v6, 0x0

    invoke-static {v5, v6, v3}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/array/ChannelList;

    invoke-virtual {p0}, Lstarcom/snd/dialog/ChannelsDialog;->getActivity()Landroid/app/Activity;

    move-result-object v1

    const-string/jumbo v2, "channel_list.properties"

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v5, 0x0

    aput-object v1, v3, v5

    const/4 v5, 0x1

    aput-object v2, v3, v5

    const/16 v5, 0x37

    invoke-static {v5, v0, v3}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 63
    const/4 v3, 0x0

    const/16 v5, 0x38

    const/4 v6, 0x0

    invoke-static {v5, v6, v3}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/array/ChannelList;

    invoke-virtual {p0}, Lstarcom/snd/dialog/ChannelsDialog;->getActivity()Landroid/app/Activity;

    move-result-object v1

    const-string/jumbo v2, "channel_list_default.properties"

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v5, 0x0

    aput-object v1, v3, v5

    const/4 v5, 0x1

    aput-object v2, v3, v5

    const/16 v5, 0x39

    invoke-static {v5, v0, v3}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 64
    return-void
.end method
