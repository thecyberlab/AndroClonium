.class public Lstarcom/snd/WebRadio;
.super Landroid/app/Activity;
.source "WebRadio.java"

# interfaces
.implements Landroid/view/View$OnClickListener;
.implements Lstarcom/snd/listener/StateListener;
.implements Lstarcom/snd/listener/CallbackListener;


# static fields
.field static lastPlayChannel:Lstarcom/snd/WebRadioChannel;

.field static lastSelectedChannel:Lstarcom/snd/WebRadioChannel;

.field static mNM:Landroid/app/NotificationManager;


# instance fields
.field private NOTIFICATION:I

.field bPlayButton:Z

.field choice:Landroid/widget/Spinner;

.field label:Landroid/widget/TextView;

.field playButton:Landroid/widget/Button;

.field progress:I

.field setButton:Landroid/widget/Button;

.field streamPlayer:Lstarcom/snd/WebStreamPlayer;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 32
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 34
    const v0, 0x7f050002

    iput v0, p0, Lstarcom/snd/WebRadio;->NOTIFICATION:I

    .line 42
    const/4 v0, 0x0

    iput-boolean v0, p0, Lstarcom/snd/WebRadio;->bPlayButton:Z

    .line 46
    const/16 v0, 0x64

    iput v0, p0, Lstarcom/snd/WebRadio;->progress:I

    return-void
.end method

.method private createSpinnerListener()Landroid/widget/AdapterView$OnItemSelectedListener;
    .locals 1

    .prologue
    .line 80
    new-instance v0, Lstarcom/snd/WebRadio$1;

    invoke-direct {v0, p0}, Lstarcom/snd/WebRadio$1;-><init>(Lstarcom/snd/WebRadio;)V

    .line 93
    .local v0, "l":Landroid/widget/AdapterView$OnItemSelectedListener;
    return-object v0
.end method

.method private showNotification()V
    .locals 6

    .prologue
    .line 182
    const/4 v2, 0x0

    new-instance v3, Landroid/content/Intent;

    const-class v4, Lstarcom/snd/WebRadio;

    invoke-direct {v3, p0, v4}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    const/high16 v4, 0x8000000

    invoke-static {p0, v2, v3, v4}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v0

    .line 183
    .local v0, "contentIntent":Landroid/app/PendingIntent;
    sget-object v2, Lstarcom/snd/WebRadio;->mNM:Landroid/app/NotificationManager;

    if-nez v2, :cond_0

    .line 185
    const-string/jumbo v2, "notification"

    invoke-virtual {p0, v2}, Lstarcom/snd/WebRadio;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/app/NotificationManager;

    sput-object v2, Lstarcom/snd/WebRadio;->mNM:Landroid/app/NotificationManager;

    .line 187
    :cond_0
    new-instance v2, Landroid/app/Notification$Builder;

    invoke-direct {v2, p0}, Landroid/app/Notification$Builder;-><init>(Landroid/content/Context;)V

    const v3, 0x7f010006

    .line 188
    invoke-virtual {v2, v3}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    move-result-object v2

    const-string/jumbo v3, "StreamPlayer"

    .line 189
    invoke-virtual {v2, v3}, Landroid/app/Notification$Builder;->setTicker(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object v2

    .line 190
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v4

    invoke-virtual {v2, v4, v5}, Landroid/app/Notification$Builder;->setWhen(J)Landroid/app/Notification$Builder;

    move-result-object v2

    const-string/jumbo v3, "StreamPlayer"

    .line 191
    invoke-virtual {v2, v3}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object v2

    const-string/jumbo v3, "StreamPlayer"

    .line 192
    invoke-virtual {v2, v3}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object v2

    .line 193
    invoke-virtual {v2, v0}, Landroid/app/Notification$Builder;->setContentIntent(Landroid/app/PendingIntent;)Landroid/app/Notification$Builder;

    move-result-object v2

    const/4 v3, 0x1

    .line 194
    invoke-virtual {v2, v3}, Landroid/app/Notification$Builder;->setOngoing(Z)Landroid/app/Notification$Builder;

    move-result-object v2

    .line 195
    invoke-virtual {v2}, Landroid/app/Notification$Builder;->build()Landroid/app/Notification;

    move-result-object v1

    .line 196
    .local v1, "notification":Landroid/app/Notification;
    sget-object v2, Lstarcom/snd/WebRadio;->mNM:Landroid/app/NotificationManager;

    iget v3, p0, Lstarcom/snd/WebRadio;->NOTIFICATION:I

    invoke-virtual {v2, v3, v1}, Landroid/app/NotificationManager;->notify(ILandroid/app/Notification;)V

    .line 197
    return-void
.end method

.method private updateSpinner()V
    .locals 9

    .prologue
    .line 110
    sget-object v2, Lstarcom/snd/WebRadio;->lastSelectedChannel:Lstarcom/snd/WebRadioChannel;

    .line 111
    .local v2, "selChannel":Lstarcom/snd/WebRadioChannel;
    iget-object v4, p0, Lstarcom/snd/WebRadio;->choice:Landroid/widget/Spinner;

    invoke-virtual {v4}, Landroid/widget/Spinner;->getAdapter()Landroid/widget/SpinnerAdapter;

    move-result-object v0

    check-cast v0, Lstarcom/snd/array/SimpleArrayAdapter;

    .line 112
    .local v0, "adapter":Lstarcom/snd/array/SimpleArrayAdapter;
    const/4 v5, 0x0

    const/16 v7, 0x0

    invoke-static {v7, v0, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 113
    const/4 v5, 0x0

    const/16 v7, 0x1

    const/4 v8, 0x0

    invoke-static {v7, v8, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lstarcom/snd/array/ChannelList;

    const/4 v5, 0x0

    const/16 v7, 0x2

    invoke-static {v7, v4, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/ArrayList;

    .line 114
    .local v3, "selectedChannels":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v7, 0x0

    aput-object v3, v5, v7

    const/16 v7, 0x3

    invoke-static {v7, v0, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 115
    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->indexOf(Ljava/lang/Object;)I

    move-result v1

    .line 116
    .local v1, "idx":I
    if-ltz v1, :cond_0

    iget-object v4, p0, Lstarcom/snd/WebRadio;->choice:Landroid/widget/Spinner;

    invoke-virtual {v4, v1}, Landroid/widget/Spinner;->setSelection(I)V

    .line 117
    :cond_0
    return-void
.end method


# virtual methods
.method hideNotification()V
    .locals 2

    .prologue
    .line 201
    sget-object v0, Lstarcom/snd/WebRadio;->mNM:Landroid/app/NotificationManager;

    if-nez v0, :cond_0

    .line 204
    :goto_0
    return-void

    .line 202
    :cond_0
    sget-object v0, Lstarcom/snd/WebRadio;->mNM:Landroid/app/NotificationManager;

    iget v1, p0, Lstarcom/snd/WebRadio;->NOTIFICATION:I

    invoke-virtual {v0, v1}, Landroid/app/NotificationManager;->cancel(I)V

    .line 203
    const/4 v0, 0x0

    sput-object v0, Lstarcom/snd/WebRadio;->mNM:Landroid/app/NotificationManager;

    goto :goto_0
.end method

.method public onCallback()V
    .locals 0

    .prologue
    .line 105
    invoke-direct {p0}, Lstarcom/snd/WebRadio;->updateSpinner()V

    .line 106
    return-void
.end method

.method public onClick(Landroid/view/View;)V
    .locals 13
    .param p1, "v"    # Landroid/view/View;

    .prologue
    const/4 v4, 0x0

    .line 122
    iget-object v0, p0, Lstarcom/snd/WebRadio;->setButton:Landroid/widget/Button;

    if-ne p1, v0, :cond_1

    .line 124
    invoke-virtual {p0}, Lstarcom/snd/WebRadio;->getFragmentManager()Landroid/app/FragmentManager;

    move-result-object v1

    const-string/jumbo v2, "fragment_settings"

    const-class v3, Lstarcom/snd/dialog/SettingsDialog;

    sget-object v5, Lstarcom/snd/dialog/SettingsDialog$SettingsType;->Main:Lstarcom/snd/dialog/SettingsDialog$SettingsType;

    move-object v0, p1

    move-object v4, p0

    invoke-static/range {v0 .. v5}, Lstarcom/snd/dialog/SettingsDialog;->showSettings(Landroid/view/View;Landroid/app/FragmentManager;Ljava/lang/String;Ljava/lang/Class;Lstarcom/snd/listener/CallbackListener;Lstarcom/snd/dialog/SettingsDialog$SettingsType;)V

    .line 154
    :cond_0
    :goto_0
    return-void

    .line 127
    :cond_1
    iget-boolean v0, p0, Lstarcom/snd/WebRadio;->bPlayButton:Z

    if-eqz v0, :cond_3

    .line 131
    :try_start_0
    iget-object v0, p0, Lstarcom/snd/WebRadio;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    const/4 v9, 0x0

    const/16 v11, 0x4

    invoke-static {v11, v0, v9}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Stopped:Lstarcom/snd/WebStreamPlayer$State;

    if-eq v0, v1, :cond_2

    .line 133
    new-instance v0, Ljava/lang/IllegalStateException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "Player is busy on state: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lstarcom/snd/WebRadio;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    const/4 v9, 0x0

    const/16 v11, 0x5

    invoke-static {v11, v2, v9}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lstarcom/snd/WebStreamPlayer$State;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 140
    :catch_0
    move-exception v7

    .line 142
    .local v7, "e":Ljava/lang/Exception;
    invoke-virtual {p0}, Lstarcom/snd/WebRadio;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    const-string/jumbo v1, "Player is busy"

    invoke-static {v0, v1, v4}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 143
    const-class v0, Lstarcom/snd/WebRadio;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v3, "Cant play because player is busy: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v4

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->warn(Ljava/lang/Class;[Ljava/lang/String;)V

    goto :goto_0

    .line 135
    .end local v7    # "e":Ljava/lang/Exception;
    :cond_2
    :try_start_1
    iget-object v0, p0, Lstarcom/snd/WebRadio;->choice:Landroid/widget/Spinner;

    invoke-virtual {v0}, Landroid/widget/Spinner;->getSelectedItem()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Lstarcom/snd/WebRadioChannel;

    .line 136
    .local v6, "curChannel":Lstarcom/snd/WebRadioChannel;
    iget-object v0, p0, Lstarcom/snd/WebRadio;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    const/4 v9, 0x0

    const/16 v11, 0x6

    invoke-static {v11, v6, v9}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    const/4 v9, 0x1

    new-array v9, v9, [Ljava/lang/Object;

    const/4 v11, 0x0

    aput-object v1, v9, v11

    const/16 v11, 0x7

    invoke-static {v11, v0, v9}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 137
    sput-object v6, Lstarcom/snd/WebRadio;->lastPlayChannel:Lstarcom/snd/WebRadioChannel;

    .line 138
    iget-object v0, p0, Lstarcom/snd/WebRadio;->label:Landroid/widget/TextView;

    sget-object v1, Lstarcom/snd/WebRadio;->lastPlayChannel:Lstarcom/snd/WebRadioChannel;

    const/4 v9, 0x0

    const/16 v11, 0x8

    invoke-static {v11, v1, v9}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto/16 :goto_0

    .line 148
    .end local v6    # "curChannel":Lstarcom/snd/WebRadioChannel;
    :cond_3
    iget-object v0, p0, Lstarcom/snd/WebRadio;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    const/4 v9, 0x0

    const/16 v11, 0x9

    invoke-static {v11, v0, v9}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/lang/Boolean;

    invoke-virtual {v8}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v8

    .line 149
    .local v8, "result":Z
    if-nez v8, :cond_0

    .line 151
    invoke-virtual {p0}, Lstarcom/snd/WebRadio;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    const-string/jumbo v1, "Player is busy"

    invoke-static {v0, v1, v4}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    goto/16 :goto_0
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 9
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 52
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 53
    const/high16 v3, 0x7f030000

    invoke-virtual {p0, v3}, Lstarcom/snd/WebRadio;->setContentView(I)V

    .line 54
    const v3, 0x7f02000f

    invoke-virtual {p0, v3}, Lstarcom/snd/WebRadio;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/LinearLayout;

    .line 55
    .local v1, "rl":Landroid/widget/LinearLayout;
    const v3, -0x333334

    invoke-virtual {v1, v3}, Landroid/widget/LinearLayout;->setBackgroundColor(I)V

    .line 56
    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v7, 0x0

    aput-object p0, v5, v7

    const/16 v7, 0xa

    const/4 v8, 0x0

    invoke-static {v7, v8, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 58
    const v3, 0x7f020010

    invoke-virtual {p0, v3}, Lstarcom/snd/WebRadio;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/Button;

    iput-object v3, p0, Lstarcom/snd/WebRadio;->playButton:Landroid/widget/Button;

    .line 59
    iget-object v3, p0, Lstarcom/snd/WebRadio;->playButton:Landroid/widget/Button;

    invoke-virtual {v3, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 60
    const v3, 0x7f020013

    invoke-virtual {p0, v3}, Lstarcom/snd/WebRadio;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/TextView;

    iput-object v3, p0, Lstarcom/snd/WebRadio;->label:Landroid/widget/TextView;

    .line 61
    iget-object v3, p0, Lstarcom/snd/WebRadio;->label:Landroid/widget/TextView;

    const-string/jumbo v4, "WebStreamPlayer"

    invoke-virtual {v3, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 62
    const v3, 0x7f020011

    invoke-virtual {p0, v3}, Lstarcom/snd/WebRadio;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/Button;

    iput-object v3, p0, Lstarcom/snd/WebRadio;->setButton:Landroid/widget/Button;

    .line 63
    iget-object v3, p0, Lstarcom/snd/WebRadio;->setButton:Landroid/widget/Button;

    invoke-virtual {v3, p0}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 64
    const v3, 0x7f020012

    invoke-virtual {p0, v3}, Lstarcom/snd/WebRadio;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/Spinner;

    iput-object v3, p0, Lstarcom/snd/WebRadio;->choice:Landroid/widget/Spinner;

    .line 66
    new-instance v0, Lstarcom/snd/array/SimpleArrayAdapter;

    invoke-virtual {p0}, Lstarcom/snd/WebRadio;->getApplicationContext()Landroid/content/Context;

    move-result-object v3

    invoke-direct {v0, v3}, Lstarcom/snd/array/SimpleArrayAdapter;-><init>(Landroid/content/Context;)V

    .line 67
    .local v0, "arrayAdapter":Lstarcom/snd/array/SimpleArrayAdapter;
    iget-object v3, p0, Lstarcom/snd/WebRadio;->choice:Landroid/widget/Spinner;

    invoke-virtual {v3, v0}, Landroid/widget/Spinner;->setAdapter(Landroid/widget/SpinnerAdapter;)V

    .line 68
    iget-object v3, p0, Lstarcom/snd/WebRadio;->choice:Landroid/widget/Spinner;

    invoke-direct {p0}, Lstarcom/snd/WebRadio;->createSpinnerListener()Landroid/widget/AdapterView$OnItemSelectedListener;

    move-result-object v4

    invoke-virtual {v3, v4}, Landroid/widget/Spinner;->setOnItemSelectedListener(Landroid/widget/AdapterView$OnItemSelectedListener;)V

    .line 70
    const/4 v5, 0x0

    const/16 v7, 0xb

    const/4 v8, 0x0

    invoke-static {v7, v8, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lstarcom/snd/WebStreamPlayer;

    iput-object v3, p0, Lstarcom/snd/WebRadio;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    .line 71
    iget-object v3, p0, Lstarcom/snd/WebRadio;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    invoke-virtual {v3, p0}, Lstarcom/snd/WebStreamPlayer;->setStateListener(Lstarcom/snd/listener/StateListener;)V

    .line 72
    iget-object v3, p0, Lstarcom/snd/WebRadio;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    const/4 v5, 0x0

    const/16 v7, 0xc

    invoke-static {v7, v3, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lstarcom/snd/WebStreamPlayer$State;

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v7, 0x0

    aput-object v3, v5, v7

    const/16 v7, 0xd

    invoke-static {v7, p0, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 74
    const-string/jumbo v3, "phone"

    invoke-virtual {p0, v3}, Lstarcom/snd/WebRadio;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/telephony/TelephonyManager;

    .line 75
    .local v2, "telephonyManager":Landroid/telephony/TelephonyManager;
    new-instance v3, Lstarcom/snd/listener/CallStateListener;

    iget-object v4, p0, Lstarcom/snd/WebRadio;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    invoke-direct {v3, v4}, Lstarcom/snd/listener/CallStateListener;-><init>(Lstarcom/snd/WebStreamPlayer;)V

    const/16 v4, 0x20

    invoke-virtual {v2, v3, v4}, Landroid/telephony/TelephonyManager;->listen(Landroid/telephony/PhoneStateListener;I)V

    .line 76
    return-void
.end method

.method public onResume()V
    .locals 0

    .prologue
    .line 99
    invoke-super {p0}, Landroid/app/Activity;->onResume()V

    .line 100
    invoke-direct {p0}, Lstarcom/snd/WebRadio;->updateSpinner()V

    .line 101
    return-void
.end method

.method public stateChanged(Lstarcom/snd/WebStreamPlayer$State;)V
    .locals 5
    .param p1, "state"    # Lstarcom/snd/WebStreamPlayer$State;

    .prologue
    const/4 v2, 0x1

    const/4 v4, 0x0

    .line 159
    sget-object v0, Lstarcom/snd/WebStreamPlayer$State;->Playing:Lstarcom/snd/WebStreamPlayer$State;

    if-ne p1, v0, :cond_1

    .line 161
    iget-object v0, p0, Lstarcom/snd/WebRadio;->playButton:Landroid/widget/Button;

    const v1, 0x7f05000e

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setText(I)V

    .line 162
    iput-boolean v4, p0, Lstarcom/snd/WebRadio;->bPlayButton:Z

    .line 163
    invoke-direct {p0}, Lstarcom/snd/WebRadio;->showNotification()V

    .line 178
    :cond_0
    :goto_0
    return-void

    .line 165
    :cond_1
    sget-object v0, Lstarcom/snd/WebStreamPlayer$State;->Stopped:Lstarcom/snd/WebStreamPlayer$State;

    if-ne p1, v0, :cond_2

    .line 167
    iget-object v0, p0, Lstarcom/snd/WebRadio;->playButton:Landroid/widget/Button;

    const v1, 0x7f050009

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setText(I)V

    .line 168
    iput-boolean v2, p0, Lstarcom/snd/WebRadio;->bPlayButton:Z

    .line 169
    iget-object v0, p0, Lstarcom/snd/WebRadio;->label:Landroid/widget/TextView;

    const-string/jumbo v1, "WebStreamPlayer"

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 170
    invoke-virtual {p0}, Lstarcom/snd/WebRadio;->hideNotification()V

    goto :goto_0

    .line 172
    :cond_2
    sget-object v0, Lstarcom/snd/WebStreamPlayer$State;->Preparing:Lstarcom/snd/WebStreamPlayer$State;

    if-eq p1, v0, :cond_0

    .line 173
    sget-object v0, Lstarcom/snd/WebStreamPlayer$State;->Pause:Lstarcom/snd/WebStreamPlayer$State;

    if-eq p1, v0, :cond_0

    .line 176
    const-class v0, Lstarcom/snd/WebRadio;

    new-array v1, v2, [Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v3, "Error, unknown State: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v4

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->severe(Ljava/lang/Class;[Ljava/lang/String;)V

    goto :goto_0
.end method

.method public stateLoading(I)V
    .locals 3
    .param p1, "percent"    # I

    .prologue
    .line 209
    if-eqz p1, :cond_0

    iget v0, p0, Lstarcom/snd/WebRadio;->progress:I

    if-gt p1, v0, :cond_0

    .line 212
    :goto_0
    return-void

    .line 210
    :cond_0
    iput p1, p0, Lstarcom/snd/WebRadio;->progress:I

    .line 211
    invoke-virtual {p0}, Lstarcom/snd/WebRadio;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "Loading: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string/jumbo v2, "%"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    goto :goto_0
.end method
