.class public Lstarcom/snd/WebStreamPlayer;
.super Ljava/lang/Object;
.source "WebStreamPlayer.java"

# interfaces
.implements Landroid/media/MediaPlayer$OnBufferingUpdateListener;
.implements Landroid/media/MediaPlayer$OnCompletionListener;
.implements Landroid/media/MediaPlayer$OnErrorListener;
.implements Landroid/media/MediaPlayer$OnInfoListener;
.implements Landroid/media/MediaPlayer$OnPreparedListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lstarcom/snd/WebStreamPlayer$State;
    }
.end annotation


# static fields
.field private static instance:Lstarcom/snd/WebStreamPlayer;


# instance fields
.field private curState:Lstarcom/snd/WebStreamPlayer$State;

.field private mediaPlayer:Landroid/media/MediaPlayer;

.field stateListener:Lstarcom/snd/listener/StateListener;


# direct methods
.method private constructor <init>()V
    .locals 1

    .prologue
    .line 29
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 20
    sget-object v0, Lstarcom/snd/WebStreamPlayer$State;->Stopped:Lstarcom/snd/WebStreamPlayer$State;

    iput-object v0, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    .line 29
    return-void
.end method

.method public static getInstance()Lstarcom/snd/WebStreamPlayer;
    .locals 1

    .prologue
    .line 33
    sget-object v0, Lstarcom/snd/WebStreamPlayer;->instance:Lstarcom/snd/WebStreamPlayer;

    if-nez v0, :cond_0

    .line 35
    new-instance v0, Lstarcom/snd/WebStreamPlayer;

    invoke-direct {v0}, Lstarcom/snd/WebStreamPlayer;-><init>()V

    sput-object v0, Lstarcom/snd/WebStreamPlayer;->instance:Lstarcom/snd/WebStreamPlayer;

    .line 37
    :cond_0
    sget-object v0, Lstarcom/snd/WebStreamPlayer;->instance:Lstarcom/snd/WebStreamPlayer;

    return-object v0
.end method

.method private getMediaPlayer()Landroid/media/MediaPlayer;
    .locals 2

    .prologue
    .line 70
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    if-nez v0, :cond_0

    .line 72
    new-instance v0, Landroid/media/MediaPlayer;

    invoke-direct {v0}, Landroid/media/MediaPlayer;-><init>()V

    iput-object v0, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    .line 73
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0, p0}, Landroid/media/MediaPlayer;->setOnBufferingUpdateListener(Landroid/media/MediaPlayer$OnBufferingUpdateListener;)V

    .line 74
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0, p0}, Landroid/media/MediaPlayer;->setOnCompletionListener(Landroid/media/MediaPlayer$OnCompletionListener;)V

    .line 75
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0, p0}, Landroid/media/MediaPlayer;->setOnErrorListener(Landroid/media/MediaPlayer$OnErrorListener;)V

    .line 76
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0, p0}, Landroid/media/MediaPlayer;->setOnInfoListener(Landroid/media/MediaPlayer$OnInfoListener;)V

    .line 77
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0, p0}, Landroid/media/MediaPlayer;->setOnPreparedListener(Landroid/media/MediaPlayer$OnPreparedListener;)V

    .line 78
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/media/MediaPlayer;->setAudioStreamType(I)V

    .line 80
    :cond_0
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    return-object v0
.end method

.method private setState(Lstarcom/snd/WebStreamPlayer$State;)V
    .locals 5
    .param p1, "state"    # Lstarcom/snd/WebStreamPlayer$State;

    .prologue
    .line 90
    iput-object p1, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    .line 91
    const-class v0, Lstarcom/snd/WebStreamPlayer;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v4, "Set to new State: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 92
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->stateListener:Lstarcom/snd/listener/StateListener;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->stateListener:Lstarcom/snd/listener/StateListener;

    invoke-interface {v0, p1}, Lstarcom/snd/listener/StateListener;->stateChanged(Lstarcom/snd/WebStreamPlayer$State;)V

    .line 93
    :cond_0
    return-void
.end method


# virtual methods
.method public getState()Lstarcom/snd/WebStreamPlayer$State;
    .locals 1

    .prologue
    .line 125
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    return-object v0
.end method

.method public onBufferingUpdate(Landroid/media/MediaPlayer;I)V
    .locals 2
    .param p1, "mp"    # Landroid/media/MediaPlayer;
    .param p2, "percent"    # I

    .prologue
    .line 178
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->stateListener:Lstarcom/snd/listener/StateListener;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->stateListener:Lstarcom/snd/listener/StateListener;

    const/16 v1, 0x32

    invoke-interface {v0, v1}, Lstarcom/snd/listener/StateListener;->stateLoading(I)V

    .line 179
    :cond_0
    return-void
.end method

.method public onCompletion(Landroid/media/MediaPlayer;)V
    .locals 4
    .param p1, "mp"    # Landroid/media/MediaPlayer;

    .prologue
    .line 172
    const/4 v0, 0x0

    const/16 v2, 0x11

    invoke-static {v2, p0, v0}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 173
    return-void
.end method

.method public onError(Landroid/media/MediaPlayer;II)Z
    .locals 7
    .param p1, "mp"    # Landroid/media/MediaPlayer;
    .param p2, "what"    # I
    .param p3, "extra"    # I

    .prologue
    .line 145
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Playing:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v0, v1, :cond_1

    .line 147
    const/4 v3, 0x0

    const/16 v5, 0x12

    invoke-static {v5, p0, v3}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 165
    :cond_0
    :goto_0
    const/4 v0, 0x0

    return v0

    .line 149
    :cond_1
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Preparing:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v0, v1, :cond_2

    .line 151
    const/4 v3, 0x0

    const/16 v5, 0x13

    invoke-static {v5, p0, v3}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    .line 153
    :cond_2
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Pause:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v0, v1, :cond_3

    .line 155
    const/4 v3, 0x0

    const/16 v5, 0x14

    invoke-static {v5, p0, v3}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    .line 157
    :cond_3
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Stopped:Lstarcom/snd/WebStreamPlayer$State;

    if-eq v0, v1, :cond_0

    .line 163
    new-instance v0, Ljava/lang/IllegalStateException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "Unknown State: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public onInfo(Landroid/media/MediaPlayer;II)Z
    .locals 1
    .param p1, "mp"    # Landroid/media/MediaPlayer;
    .param p2, "what"    # I
    .param p3, "extra"    # I

    .prologue
    .line 139
    const/4 v0, 0x0

    return v0
.end method

.method public onPrepared(Landroid/media/MediaPlayer;)V
    .locals 4
    .param p1, "mp"    # Landroid/media/MediaPlayer;

    .prologue
    .line 130
    const-class v0, Lstarcom/snd/WebStreamPlayer;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    const-string/jumbo v3, "Start playing of MediaPlayer."

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 131
    sget-object v0, Lstarcom/snd/WebStreamPlayer$State;->Playing:Lstarcom/snd/WebStreamPlayer$State;

    invoke-direct {p0, v0}, Lstarcom/snd/WebStreamPlayer;->setState(Lstarcom/snd/WebStreamPlayer$State;)V

    .line 132
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->stateListener:Lstarcom/snd/listener/StateListener;

    const/16 v1, 0x64

    invoke-interface {v0, v1}, Lstarcom/snd/listener/StateListener;->stateLoading(I)V

    .line 133
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->start()V

    .line 134
    return-void
.end method

.method public declared-synchronized pause(Z)Z
    .locals 3
    .param p1, "doPause"    # Z

    .prologue
    const/4 v0, 0x1

    .line 97
    monitor-enter p0

    if-eqz p1, :cond_0

    :try_start_0
    iget-object v1, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v2, Lstarcom/snd/WebStreamPlayer$State;->Playing:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v1, v2, :cond_0

    .line 99
    invoke-direct {p0}, Lstarcom/snd/WebStreamPlayer;->getMediaPlayer()Landroid/media/MediaPlayer;

    move-result-object v1

    invoke-virtual {v1}, Landroid/media/MediaPlayer;->pause()V

    .line 100
    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Pause:Lstarcom/snd/WebStreamPlayer$State;

    invoke-direct {p0, v1}, Lstarcom/snd/WebStreamPlayer;->setState(Lstarcom/snd/WebStreamPlayer$State;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 109
    :goto_0
    monitor-exit p0

    return v0

    .line 103
    :cond_0
    if-nez p1, :cond_1

    :try_start_1
    iget-object v1, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v2, Lstarcom/snd/WebStreamPlayer$State;->Pause:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v1, v2, :cond_1

    .line 105
    invoke-direct {p0}, Lstarcom/snd/WebStreamPlayer;->getMediaPlayer()Landroid/media/MediaPlayer;

    move-result-object v1

    invoke-virtual {v1}, Landroid/media/MediaPlayer;->start()V

    .line 106
    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Playing:Lstarcom/snd/WebStreamPlayer$State;

    invoke-direct {p0, v1}, Lstarcom/snd/WebStreamPlayer;->setState(Lstarcom/snd/WebStreamPlayer$State;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    .line 97
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0

    .line 109
    :cond_1
    const/4 v0, 0x0

    goto :goto_0
.end method

.method public declared-synchronized play(Ljava/lang/String;)V
    .locals 4
    .param p1, "url"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/IllegalArgumentException;,
            Ljava/lang/SecurityException;,
            Ljava/lang/IllegalStateException;,
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 42
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Playing:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v0, v1, :cond_0

    .line 44
    new-instance v0, Ljava/lang/IllegalStateException;

    const-string/jumbo v1, "MediaPlayer is playing, cant start now."

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 42
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0

    .line 46
    :cond_0
    :try_start_1
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Preparing:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v0, v1, :cond_1

    .line 48
    new-instance v0, Ljava/lang/IllegalStateException;

    const-string/jumbo v1, "MediaPlayer busy, cant start now."

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 50
    :cond_1
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Pause:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v0, v1, :cond_2

    .line 52
    new-instance v0, Ljava/lang/IllegalStateException;

    const-string/jumbo v1, "MediaPlayer paused, cant stop now."

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 54
    :cond_2
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Stopped:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v0, v1, :cond_3

    .line 56
    const-class v0, Lstarcom/snd/WebStreamPlayer;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    const-string/jumbo v3, "Start preparing of MediaPlayer."

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 57
    invoke-direct {p0}, Lstarcom/snd/WebStreamPlayer;->getMediaPlayer()Landroid/media/MediaPlayer;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/media/MediaPlayer;->setDataSource(Ljava/lang/String;)V

    .line 58
    sget-object v0, Lstarcom/snd/WebStreamPlayer$State;->Preparing:Lstarcom/snd/WebStreamPlayer$State;

    invoke-direct {p0, v0}, Lstarcom/snd/WebStreamPlayer;->setState(Lstarcom/snd/WebStreamPlayer$State;)V

    .line 59
    iget-object v0, p0, Lstarcom/snd/WebStreamPlayer;->stateListener:Lstarcom/snd/listener/StateListener;

    const/4 v1, 0x0

    invoke-interface {v0, v1}, Lstarcom/snd/listener/StateListener;->stateLoading(I)V

    .line 60
    invoke-direct {p0}, Lstarcom/snd/WebStreamPlayer;->getMediaPlayer()Landroid/media/MediaPlayer;

    move-result-object v0

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->prepareAsync()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 66
    monitor-exit p0

    return-void

    .line 64
    :cond_3
    :try_start_2
    new-instance v0, Ljava/lang/IllegalStateException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v2, "Unknown State: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0
.end method

.method public setStateListener(Lstarcom/snd/listener/StateListener;)V
    .locals 0
    .param p1, "stateListener"    # Lstarcom/snd/listener/StateListener;

    .prologue
    .line 85
    iput-object p1, p0, Lstarcom/snd/WebStreamPlayer;->stateListener:Lstarcom/snd/listener/StateListener;

    .line 86
    return-void
.end method

.method public declared-synchronized stop()Z
    .locals 6

    .prologue
    const/4 v1, 0x0

    const/4 v0, 0x1

    .line 114
    monitor-enter p0

    :try_start_0
    iget-object v2, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v3, Lstarcom/snd/WebStreamPlayer$State;->Stopped:Lstarcom/snd/WebStreamPlayer$State;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-ne v2, v3, :cond_1

    .line 122
    :cond_0
    :goto_0
    monitor-exit p0

    return v0

    .line 115
    :cond_1
    :try_start_1
    const-class v2, Lstarcom/snd/WebStreamPlayer;

    const/4 v3, 0x1

    new-array v3, v3, [Ljava/lang/String;

    const/4 v4, 0x0

    const-string/jumbo v5, "Stop MediaPlayer."

    aput-object v5, v3, v4

    invoke-static {v2, v3}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 116
    sget-object v2, Lstarcom/snd/WebStreamPlayer$State;->Stopped:Lstarcom/snd/WebStreamPlayer$State;

    invoke-direct {p0, v2}, Lstarcom/snd/WebStreamPlayer;->setState(Lstarcom/snd/WebStreamPlayer$State;)V

    .line 117
    iget-object v2, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    if-eqz v2, :cond_0

    .line 118
    iget-object v2, p0, Lstarcom/snd/WebStreamPlayer;->curState:Lstarcom/snd/WebStreamPlayer$State;

    sget-object v3, Lstarcom/snd/WebStreamPlayer$State;->Preparing:Lstarcom/snd/WebStreamPlayer$State;

    if-ne v2, v3, :cond_2

    move v0, v1

    goto :goto_0

    .line 119
    :cond_2
    iget-object v1, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v1}, Landroid/media/MediaPlayer;->pause()V

    .line 120
    iget-object v1, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v1}, Landroid/media/MediaPlayer;->stop()V

    .line 121
    iget-object v1, p0, Lstarcom/snd/WebStreamPlayer;->mediaPlayer:Landroid/media/MediaPlayer;

    invoke-virtual {v1}, Landroid/media/MediaPlayer;->reset()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    .line 114
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method
