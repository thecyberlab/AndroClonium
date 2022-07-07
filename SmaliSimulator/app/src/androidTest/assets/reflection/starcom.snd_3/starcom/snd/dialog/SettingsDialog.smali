.class public Lstarcom/snd/dialog/SettingsDialog;
.super Lstarcom/snd/listener/DialogFragmentWithListener;
.source "SettingsDialog.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lstarcom/snd/dialog/SettingsDialog$SettingsType;
    }
.end annotation


# static fields
.field private static settingsType:Lstarcom/snd/dialog/SettingsDialog$SettingsType;


# instance fields
.field private channelIcon:Landroid/widget/Spinner;

.field private channelName:Landroid/widget/EditText;

.field private channelUrl:Landroid/widget/EditText;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 25
    sget-object v0, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->Main:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    sput-object v0, Lstarcom/snd/dialog/SettingsDialog;->settingsType:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .prologue
    .line 30
    invoke-direct {p0}, Lstarcom/snd/listener/DialogFragmentWithListener;-><init>()V

    return-void
.end method

.method public static showSettings(Landroid/view/View;Landroid/app/FragmentManager;Ljava/lang/String;Ljava/lang/Class;Lstarcom/snd/listener/CallbackListener;Lstarcom/snd/dialog/SettingsDialog$SettingsType;)V
    .locals 8
    .param p0, "v"    # Landroid/view/View;
    .param p1, "fm"    # Landroid/app/FragmentManager;
    .param p2, "dialogKey"    # Ljava/lang/String;
    .param p4, "callback"    # Lstarcom/snd/listener/CallbackListener;
    .param p5, "settingsTypeObj"    # Lstarcom/snd/dialog/SettingsDialog$SettingsType;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/view/View;",
            "Landroid/app/FragmentManager;",
            "Ljava/lang/String;",
            "Ljava/lang/Class",
            "<*>;",
            "Lstarcom/snd/listener/CallbackListener;",
            "Lstarcom/snd/dialog/SettingsDialog$SettingsType;",
            ")V"
        }
    .end annotation

    .prologue
    .line 146
    .local p3, "c":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    if-eqz p5, :cond_0

    sput-object p5, Lstarcom/snd/dialog/SettingsDialog;->settingsType:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    .line 147
    :cond_0
    invoke-virtual {p1, p2}, Landroid/app/FragmentManager;->findFragmentByTag(Ljava/lang/String;)Landroid/app/Fragment;

    move-result-object v2

    .line 148
    .local v2, "frag":Landroid/app/Fragment;
    if-eqz v2, :cond_1

    .line 150
    invoke-virtual {p1}, Landroid/app/FragmentManager;->beginTransaction()Landroid/app/FragmentTransaction;

    move-result-object v3

    invoke-virtual {v3, v2}, Landroid/app/FragmentTransaction;->remove(Landroid/app/Fragment;)Landroid/app/FragmentTransaction;

    move-result-object v3

    invoke-virtual {v3}, Landroid/app/FragmentTransaction;->commit()I

    .line 155
    :cond_1
    :try_start_0
    invoke-virtual {p3}, Ljava/lang/Class;->newInstance()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lstarcom/snd/listener/DialogFragmentWithListener;

    .line 156
    .local v1, "editNameDialog":Lstarcom/snd/listener/DialogFragmentWithListener;
    invoke-virtual {v1, p1, p2}, Lstarcom/snd/listener/DialogFragmentWithListener;->show(Landroid/app/FragmentManager;Ljava/lang/String;)V

    .line 157
    invoke-virtual {v1, p4}, Lstarcom/snd/listener/DialogFragmentWithListener;->setCallbackListener(Lstarcom/snd/listener/CallbackListener;)Lstarcom/snd/listener/CallbackListener;
    :try_end_0
    .catch Ljava/lang/InstantiationException; {:try_start_0 .. :try_end_0} :catch_0
    .catch Ljava/lang/IllegalAccessException; {:try_start_0 .. :try_end_0} :catch_1

    .line 164
    .end local v1    # "editNameDialog":Lstarcom/snd/listener/DialogFragmentWithListener;
    :goto_0
    return-void

    .line 160
    :catch_0
    move-exception v0

    .line 162
    .local v0, "e":Ljava/lang/ReflectiveOperationException;
    :goto_1
    const-class v3, Lstarcom/snd/dialog/SettingsDialog;

    const/4 v4, 0x1

    new-array v4, v4, [Ljava/lang/String;

    const/4 v5, 0x0

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v7, "Error creating class: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    aput-object v6, v4, v5

    invoke-static {v3, v0, v4}, Lstarcom/debug/LoggingSystem;->severe(Ljava/lang/Class;Ljava/lang/Exception;[Ljava/lang/String;)V

    goto :goto_0

    .line 160
    .end local v0    # "e":Ljava/lang/ReflectiveOperationException;
    :catch_1
    move-exception v0

    goto :goto_1
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 14
    .param p1, "v"    # Landroid/view/View;

    .prologue
    .line 89
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const v1, 0x7f02000b

    if-ne v0, v1, :cond_1

    .line 91
    const-class v0, Lstarcom/snd/dialog/SettingsDialog;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    const-string/jumbo v3, "Setting selected: addChannel"

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 92
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lstarcom/snd/dialog/SettingsDialog;->setCallbackListener(Lstarcom/snd/listener/CallbackListener;)Lstarcom/snd/listener/CallbackListener;

    move-result-object v4

    .line 93
    .local v4, "delegateCallback":Lstarcom/snd/listener/CallbackListener;
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->dismiss()V

    .line 94
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->getFragmentManager()Landroid/app/FragmentManager;

    move-result-object v1

    const-string/jumbo v2, "fragment_channels"

    const-class v3, Lstarcom/snd/dialog/ChannelsDialog;

    const/4 v5, 0x0

    move-object v0, p1

    invoke-static/range {v0 .. v5}, Lstarcom/snd/dialog/SettingsDialog;->showSettings(Landroid/view/View;Landroid/app/FragmentManager;Ljava/lang/String;Ljava/lang/Class;Lstarcom/snd/listener/CallbackListener;Lstarcom/snd/dialog/SettingsDialog$SettingsType;)V

    .line 139
    .end local v4    # "delegateCallback":Lstarcom/snd/listener/CallbackListener;
    :cond_0
    :goto_0
    return-void

    .line 96
    :cond_1
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const v1, 0x7f02000a

    if-ne v0, v1, :cond_2

    .line 98
    const-class v0, Lstarcom/snd/dialog/SettingsDialog;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    const-string/jumbo v3, "Setting selected: editChannel"

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 99
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lstarcom/snd/dialog/SettingsDialog;->setCallbackListener(Lstarcom/snd/listener/CallbackListener;)Lstarcom/snd/listener/CallbackListener;

    move-result-object v4

    .line 100
    .restart local v4    # "delegateCallback":Lstarcom/snd/listener/CallbackListener;
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->dismiss()V

    .line 101
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->getFragmentManager()Landroid/app/FragmentManager;

    move-result-object v1

    const-string/jumbo v2, "fragment_edit"

    const-class v3, Lstarcom/snd/dialog/SettingsDialog;

    sget-object v5, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->EditChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    move-object v0, p1

    invoke-static/range {v0 .. v5}, Lstarcom/snd/dialog/SettingsDialog;->showSettings(Landroid/view/View;Landroid/app/FragmentManager;Ljava/lang/String;Ljava/lang/Class;Lstarcom/snd/listener/CallbackListener;Lstarcom/snd/dialog/SettingsDialog$SettingsType;)V

    goto :goto_0

    .line 103
    .end local v4    # "delegateCallback":Lstarcom/snd/listener/CallbackListener;
    :cond_2
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const v1, 0x7f020016

    if-ne v0, v1, :cond_3

    .line 105
    const-class v0, Lstarcom/snd/dialog/SettingsDialog;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    const-string/jumbo v3, "Setting selected: rmChannel"

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 106
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->dismiss()V

    .line 107
    invoke-static {}, Lstarcom/snd/array/ChannelList;->getInstance()Lstarcom/snd/array/ChannelList;

    move-result-object v0

    invoke-virtual {v0}, Lstarcom/snd/array/ChannelList;->getCustomChannelList()Ljava/util/ArrayList;

    move-result-object v0

    invoke-static {}, Lstarcom/snd/array/ChannelList;->getInstance()Lstarcom/snd/array/ChannelList;

    move-result-object v1

    invoke-virtual {v1}, Lstarcom/snd/array/ChannelList;->getSelectedChannel()Lstarcom/snd/WebRadioChannel;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    goto :goto_0

    .line 109
    :cond_3
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const v1, 0x7f020001

    if-ne v0, v1, :cond_4

    .line 111
    const-class v0, Lstarcom/snd/dialog/SettingsDialog;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    const-string/jumbo v3, "Setting selected: addChannel"

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 112
    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Lstarcom/snd/dialog/SettingsDialog;->setCallbackListener(Lstarcom/snd/listener/CallbackListener;)Lstarcom/snd/listener/CallbackListener;

    move-result-object v4

    .line 113
    .restart local v4    # "delegateCallback":Lstarcom/snd/listener/CallbackListener;
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->dismiss()V

    .line 114
    invoke-static {}, Lstarcom/snd/array/ChannelList;->getInstance()Lstarcom/snd/array/ChannelList;

    move-result-object v0

    const/4 v1, -0x1

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Lstarcom/snd/array/ChannelList;->setSelectedChannel(IZ)V

    .line 115
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->getFragmentManager()Landroid/app/FragmentManager;

    move-result-object v1

    const-string/jumbo v2, "fragment_edit"

    const-class v3, Lstarcom/snd/dialog/SettingsDialog;

    sget-object v5, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->EditChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    move-object v0, p1

    invoke-static/range {v0 .. v5}, Lstarcom/snd/dialog/SettingsDialog;->showSettings(Landroid/view/View;Landroid/app/FragmentManager;Ljava/lang/String;Ljava/lang/Class;Lstarcom/snd/listener/CallbackListener;Lstarcom/snd/dialog/SettingsDialog$SettingsType;)V

    goto/16 :goto_0

    .line 117
    .end local v4    # "delegateCallback":Lstarcom/snd/listener/CallbackListener;
    :cond_4
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const/high16 v1, 0x7f020000

    if-ne v0, v1, :cond_5

    .line 119
    const-class v0, Lstarcom/snd/dialog/SettingsDialog;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    const-string/jumbo v3, "starcom Not supported yet: About"

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 120
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->dismiss()V

    .line 121
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->getFragmentManager()Landroid/app/FragmentManager;

    move-result-object v6

    const-string/jumbo v7, "fragment_text"

    const-class v8, Lstarcom/snd/dialog/TextDialog;

    const/4 v9, 0x0

    const/4 v10, 0x0

    move-object v5, p1

    invoke-static/range {v5 .. v10}, Lstarcom/snd/dialog/SettingsDialog;->showSettings(Landroid/view/View;Landroid/app/FragmentManager;Ljava/lang/String;Ljava/lang/Class;Lstarcom/snd/listener/CallbackListener;Lstarcom/snd/dialog/SettingsDialog$SettingsType;)V

    goto/16 :goto_0

    .line 123
    :cond_5
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    const v1, 0x7f020017

    if-ne v0, v1, :cond_0

    .line 125
    iget-object v0, p0, Lstarcom/snd/dialog/SettingsDialog;->channelIcon:Landroid/widget/Spinner;

    invoke-virtual {v0}, Landroid/widget/Spinner;->getSelectedItem()Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x0

    const/4 v2, 0x4

    invoke-virtual {v0, v1, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v12

    .line 126
    .local v12, "newName":Ljava/lang/String;
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lstarcom/snd/dialog/SettingsDialog;->channelName:Landroid/widget/EditText;

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    .line 127
    new-instance v11, Lstarcom/snd/WebRadioChannel;

    iget-object v0, p0, Lstarcom/snd/dialog/SettingsDialog;->channelUrl:Landroid/widget/EditText;

    invoke-virtual {v0}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v11, v12, v0}, Lstarcom/snd/WebRadioChannel;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 128
    .local v11, "newChannel":Lstarcom/snd/WebRadioChannel;
    invoke-static {}, Lstarcom/snd/array/ChannelList;->getInstance()Lstarcom/snd/array/ChannelList;

    move-result-object v0

    invoke-virtual {v0}, Lstarcom/snd/array/ChannelList;->getSelectedChannel()Lstarcom/snd/WebRadioChannel;

    move-result-object v13

    .line 129
    .local v13, "selChannel":Lstarcom/snd/WebRadioChannel;
    if-nez v13, :cond_6

    .line 131
    invoke-static {}, Lstarcom/snd/array/ChannelList;->getInstance()Lstarcom/snd/array/ChannelList;

    move-result-object v0

    invoke-virtual {v0}, Lstarcom/snd/array/ChannelList;->getCustomChannelList()Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0, v11}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 137
    :goto_1
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->dismiss()V

    goto/16 :goto_0

    .line 135
    :cond_6
    invoke-virtual {v13, v11}, Lstarcom/snd/WebRadioChannel;->setData(Lstarcom/snd/WebRadioChannel;)V

    goto :goto_1
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 12
    .param p1, "inflater"    # Landroid/view/LayoutInflater;
    .param p2, "container"    # Landroid/view/ViewGroup;
    .param p3, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 36
    sget-object v9, Lstarcom/snd/dialog/SettingsDialog;->settingsType:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    sget-object v10, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->EditChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    if-ne v9, v10, :cond_1

    .line 38
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->getDialog()Landroid/app/Dialog;

    move-result-object v9

    const v10, 0x7f050004

    invoke-virtual {v9, v10}, Landroid/app/Dialog;->setTitle(I)V

    .line 39
    const v9, 0x7f030004

    invoke-virtual {p1, v9, p2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v8

    .line 40
    .local v8, "view":Landroid/view/View;
    const v9, 0x7f020017

    invoke-virtual {v8, v9}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v6

    check-cast v6, Landroid/widget/Button;

    .line 41
    .local v6, "saveButton":Landroid/widget/Button;
    invoke-virtual {v6, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 42
    const v9, 0x7f020003

    invoke-virtual {v8, v9}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v9

    check-cast v9, Landroid/widget/EditText;

    iput-object v9, p0, Lstarcom/snd/dialog/SettingsDialog;->channelName:Landroid/widget/EditText;

    .line 43
    const v9, 0x7f020006

    invoke-virtual {v8, v9}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v9

    check-cast v9, Landroid/widget/EditText;

    iput-object v9, p0, Lstarcom/snd/dialog/SettingsDialog;->channelUrl:Landroid/widget/EditText;

    .line 44
    const v9, 0x7f020002

    invoke-virtual {v8, v9}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v9

    check-cast v9, Landroid/widget/Spinner;

    iput-object v9, p0, Lstarcom/snd/dialog/SettingsDialog;->channelIcon:Landroid/widget/Spinner;

    .line 45
    new-instance v2, Lstarcom/snd/array/SimpleArrayAdapter;

    invoke-virtual {p1}, Landroid/view/LayoutInflater;->getContext()Landroid/content/Context;

    move-result-object v9

    invoke-direct {v2, v9}, Lstarcom/snd/array/SimpleArrayAdapter;-><init>(Landroid/content/Context;)V

    .line 46
    .local v2, "channelAdapter":Lstarcom/snd/array/SimpleArrayAdapter;
    iget-object v9, p0, Lstarcom/snd/dialog/SettingsDialog;->channelIcon:Landroid/widget/Spinner;

    invoke-virtual {v9, v2}, Landroid/widget/Spinner;->setAdapter(Landroid/widget/SpinnerAdapter;)V

    .line 47
    new-instance v9, Lstarcom/snd/WebRadioChannel;

    const-string/jumbo v10, "[e] Electro"

    const-string/jumbo v11, ""

    invoke-direct {v9, v10, v11}, Lstarcom/snd/WebRadioChannel;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v2, v9}, Lstarcom/snd/array/SimpleArrayAdapter;->add(Lstarcom/snd/WebRadioChannel;)V

    .line 48
    new-instance v9, Lstarcom/snd/WebRadioChannel;

    const-string/jumbo v10, "[r] Rock"

    const-string/jumbo v11, ""

    invoke-direct {v9, v10, v11}, Lstarcom/snd/WebRadioChannel;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v2, v9}, Lstarcom/snd/array/SimpleArrayAdapter;->add(Lstarcom/snd/WebRadioChannel;)V

    .line 49
    new-instance v9, Lstarcom/snd/WebRadioChannel;

    const-string/jumbo v10, "[o] Oldies"

    const-string/jumbo v11, ""

    invoke-direct {v9, v10, v11}, Lstarcom/snd/WebRadioChannel;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v2, v9}, Lstarcom/snd/array/SimpleArrayAdapter;->add(Lstarcom/snd/WebRadioChannel;)V

    .line 50
    new-instance v9, Lstarcom/snd/WebRadioChannel;

    const-string/jumbo v10, "[c] Classic"

    const-string/jumbo v11, ""

    invoke-direct {v9, v10, v11}, Lstarcom/snd/WebRadioChannel;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v2, v9}, Lstarcom/snd/array/SimpleArrayAdapter;->add(Lstarcom/snd/WebRadioChannel;)V

    .line 51
    new-instance v9, Lstarcom/snd/WebRadioChannel;

    const-string/jumbo v10, "[j] Jazz"

    const-string/jumbo v11, ""

    invoke-direct {v9, v10, v11}, Lstarcom/snd/WebRadioChannel;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v2, v9}, Lstarcom/snd/array/SimpleArrayAdapter;->add(Lstarcom/snd/WebRadioChannel;)V

    .line 52
    new-instance v9, Lstarcom/snd/WebRadioChannel;

    const-string/jumbo v10, "[u] Undef"

    const-string/jumbo v11, ""

    invoke-direct {v9, v10, v11}, Lstarcom/snd/WebRadioChannel;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v2, v9}, Lstarcom/snd/array/SimpleArrayAdapter;->add(Lstarcom/snd/WebRadioChannel;)V

    .line 54
    invoke-static {}, Lstarcom/snd/array/ChannelList;->getInstance()Lstarcom/snd/array/ChannelList;

    move-result-object v9

    invoke-virtual {v9}, Lstarcom/snd/array/ChannelList;->getSelectedChannel()Lstarcom/snd/WebRadioChannel;

    move-result-object v7

    .line 55
    .local v7, "selChannel":Lstarcom/snd/WebRadioChannel;
    if-eqz v7, :cond_0

    .line 57
    iget-object v9, p0, Lstarcom/snd/dialog/SettingsDialog;->channelName:Landroid/widget/EditText;

    invoke-virtual {v7}, Lstarcom/snd/WebRadioChannel;->getName()Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v9, v10}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 58
    iget-object v9, p0, Lstarcom/snd/dialog/SettingsDialog;->channelUrl:Landroid/widget/EditText;

    invoke-virtual {v7}, Lstarcom/snd/WebRadioChannel;->getUrl()Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v9, v10}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 83
    .end local v2    # "channelAdapter":Lstarcom/snd/array/SimpleArrayAdapter;
    .end local v6    # "saveButton":Landroid/widget/Button;
    .end local v7    # "selChannel":Lstarcom/snd/WebRadioChannel;
    :cond_0
    :goto_0
    return-object v8

    .line 63
    .end local v8    # "view":Landroid/view/View;
    :cond_1
    invoke-virtual {p0}, Lstarcom/snd/dialog/SettingsDialog;->getDialog()Landroid/app/Dialog;

    move-result-object v9

    const v10, 0x7f05000d

    invoke-virtual {v9, v10}, Landroid/app/Dialog;->setTitle(I)V

    .line 64
    const v9, 0x7f030006

    invoke-virtual {p1, v9, p2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v8

    .line 65
    .restart local v8    # "view":Landroid/view/View;
    const v9, 0x7f020001

    invoke-virtual {v8, v9}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/Button;

    .line 66
    .local v1, "addButton":Landroid/widget/Button;
    sget-object v9, Lstarcom/snd/dialog/SettingsDialog;->settingsType:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    sget-object v10, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->Main:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    if-ne v9, v10, :cond_2

    move-object v9, v8

    check-cast v9, Landroid/view/ViewManager;

    invoke-interface {v9, v1}, Landroid/view/ViewManager;->removeView(Landroid/view/View;)V

    .line 67
    :cond_2
    const v9, 0x7f02000a

    invoke-virtual {v8, v9}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/Button;

    .line 68
    .local v3, "editButton":Landroid/widget/Button;
    sget-object v9, Lstarcom/snd/dialog/SettingsDialog;->settingsType:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    sget-object v10, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->CustomChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    if-eq v9, v10, :cond_3

    move-object v9, v8

    check-cast v9, Landroid/view/ViewManager;

    invoke-interface {v9, v3}, Landroid/view/ViewManager;->removeView(Landroid/view/View;)V

    .line 69
    :cond_3
    const v9, 0x7f02000b

    invoke-virtual {v8, v9}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/Button;

    .line 70
    .local v4, "editCsButton":Landroid/widget/Button;
    sget-object v9, Lstarcom/snd/dialog/SettingsDialog;->settingsType:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    sget-object v10, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->Main:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    if-eq v9, v10, :cond_4

    move-object v9, v8

    check-cast v9, Landroid/view/ViewManager;

    invoke-interface {v9, v4}, Landroid/view/ViewManager;->removeView(Landroid/view/View;)V

    .line 71
    :cond_4
    const v9, 0x7f020016

    invoke-virtual {v8, v9}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v5

    check-cast v5, Landroid/widget/Button;

    .line 72
    .local v5, "rmButton":Landroid/widget/Button;
    sget-object v9, Lstarcom/snd/dialog/SettingsDialog;->settingsType:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    sget-object v10, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->CustomChannel:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    if-eq v9, v10, :cond_5

    move-object v9, v8

    check-cast v9, Landroid/view/ViewManager;

    invoke-interface {v9, v5}, Landroid/view/ViewManager;->removeView(Landroid/view/View;)V

    .line 73
    :cond_5
    const/high16 v9, 0x7f020000

    invoke-virtual {v8, v9}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    .line 74
    .local v0, "aboutButton":Landroid/widget/Button;
    sget-object v9, Lstarcom/snd/dialog/SettingsDialog;->settingsType:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    sget-object v10, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->Main:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    if-eq v9, v10, :cond_6

    move-object v9, v8

    check-cast v9, Landroid/view/ViewManager;

    invoke-interface {v9, v0}, Landroid/view/ViewManager;->removeView(Landroid/view/View;)V

    .line 76
    :goto_1
    invoke-virtual {v1, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 77
    invoke-virtual {v3, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 78
    invoke-virtual {v4, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 79
    invoke-virtual {v5, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 80
    invoke-virtual {v0, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 81
    invoke-virtual {v8}, Landroid/view/View;->forceLayout()V

    goto/16 :goto_0

    .line 75
    :cond_6
    const/high16 v9, 0x7f050000

    invoke-virtual {v0, v9}, Landroid/widget/Button;->setText(I)V

    goto :goto_1
.end method
