.class public Lstarcom/snd/listener/DialogFragmentWithListener;
.super Landroid/app/DialogFragment;
.source "DialogFragmentWithListener.java"


# instance fields
.field private l:Lstarcom/snd/listener/CallbackListener;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 6
    invoke-direct {p0}, Landroid/app/DialogFragment;-><init>()V

    return-void
.end method


# virtual methods
.method public onDismiss(Landroid/content/DialogInterface;)V
    .locals 6
    .param p1, "di"    # Landroid/content/DialogInterface;

    .prologue
    const/4 v0, 0x1

    const/4 v1, 0x0

    .line 23
    invoke-super {p0, p1}, Landroid/app/DialogFragment;->onDismiss(Landroid/content/DialogInterface;)V

    .line 24
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v2

    new-array v3, v0, [Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v5, "paul CallbackListener onDismiss() Null:"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    iget-object v5, p0, Lstarcom/snd/listener/DialogFragmentWithListener;->l:Lstarcom/snd/listener/CallbackListener;

    if-nez v5, :cond_1

    :goto_0
    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    aput-object v0, v3, v1

    invoke-static {v2, v3}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 25
    iget-object v0, p0, Lstarcom/snd/listener/DialogFragmentWithListener;->l:Lstarcom/snd/listener/CallbackListener;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lstarcom/snd/listener/DialogFragmentWithListener;->l:Lstarcom/snd/listener/CallbackListener;

    invoke-interface {v0}, Lstarcom/snd/listener/CallbackListener;->onCallback()V

    .line 26
    :cond_0
    return-void

    :cond_1
    move v0, v1

    .line 24
    goto :goto_0
.end method

.method public setCallbackListener(Lstarcom/snd/listener/CallbackListener;)Lstarcom/snd/listener/CallbackListener;
    .locals 7
    .param p1, "l"    # Lstarcom/snd/listener/CallbackListener;

    .prologue
    const/4 v2, 0x1

    const/4 v3, 0x0

    .line 14
    iget-object v0, p0, Lstarcom/snd/listener/DialogFragmentWithListener;->l:Lstarcom/snd/listener/CallbackListener;

    .line 15
    .local v0, "last":Lstarcom/snd/listener/CallbackListener;
    iput-object p1, p0, Lstarcom/snd/listener/DialogFragmentWithListener;->l:Lstarcom/snd/listener/CallbackListener;

    .line 16
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v4

    new-array v5, v2, [Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v6, "paul CallbackListener from Null="

    invoke-virtual {v1, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    if-nez v0, :cond_0

    move v1, v2

    :goto_0
    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string/jumbo v6, " to Null="

    invoke-virtual {v1, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    if-nez p1, :cond_1

    :goto_1
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    aput-object v1, v5, v3

    invoke-static {v4, v5}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 17
    return-object v0

    :cond_0
    move v1, v3

    .line 16
    goto :goto_0

    :cond_1
    move v2, v3

    goto :goto_1
.end method
