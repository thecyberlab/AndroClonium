.class public Lstarcom/snd/listener/CallStateListener;
.super Landroid/telephony/PhoneStateListener;
.source "CallStateListener.java"


# instance fields
.field private streamPlayer:Lstarcom/snd/WebStreamPlayer;


# direct methods
.method public constructor <init>(Lstarcom/snd/WebStreamPlayer;)V
    .locals 0
    .param p1, "streamPlayer"    # Lstarcom/snd/WebStreamPlayer;

    .prologue
    .line 13
    invoke-direct {p0}, Landroid/telephony/PhoneStateListener;-><init>()V

    .line 14
    iput-object p1, p0, Lstarcom/snd/listener/CallStateListener;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    .line 15
    return-void
.end method

.method private do_continue()V
    .locals 6

    .prologue
    .line 27
    iget-object v0, p0, Lstarcom/snd/listener/CallStateListener;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    const/4 v2, 0x0

    const/16 v4, 0x3b

    invoke-static {v4, v0, v2}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Pause:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v0, v1, :cond_0

    .line 29
    iget-object v0, p0, Lstarcom/snd/listener/CallStateListener;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    const/4 v1, 0x0

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v3

    const/4 v5, 0x0

    aput-object v3, v2, v5

    const/16 v4, 0x3c

    invoke-static {v4, v0, v2}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 31
    :cond_0
    return-void
.end method

.method private do_stop()V
    .locals 6

    .prologue
    .line 35
    iget-object v0, p0, Lstarcom/snd/listener/CallStateListener;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    const/4 v2, 0x0

    const/16 v4, 0x3d

    invoke-static {v4, v0, v2}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Playing:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v0, v1, :cond_0

    .line 37
    iget-object v0, p0, Lstarcom/snd/listener/CallStateListener;->streamPlayer:Lstarcom/snd/WebStreamPlayer;

    const/4 v1, 0x1

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v3

    const/4 v5, 0x0

    aput-object v3, v2, v5

    const/16 v4, 0x3e

    invoke-static {v4, v0, v2}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 39
    :cond_0
    return-void
.end method


# virtual methods
.method public onCallStateChanged(ILjava/lang/String;)V
    .locals 1
    .param p1, "state"    # I
    .param p2, "incomingNumber"    # Ljava/lang/String;

    .prologue
    .line 20
    const/4 v0, 0x2

    if-ne p1, v0, :cond_1

    invoke-direct {p0}, Lstarcom/snd/listener/CallStateListener;->do_stop()V

    .line 23
    :cond_0
    :goto_0
    return-void

    .line 21
    :cond_1
    const/4 v0, 0x1

    if-ne p1, v0, :cond_2

    invoke-direct {p0}, Lstarcom/snd/listener/CallStateListener;->do_stop()V

    goto :goto_0

    .line 22
    :cond_2
    if-nez p1, :cond_0

    invoke-direct {p0}, Lstarcom/snd/listener/CallStateListener;->do_continue()V

    goto :goto_0
.end method
