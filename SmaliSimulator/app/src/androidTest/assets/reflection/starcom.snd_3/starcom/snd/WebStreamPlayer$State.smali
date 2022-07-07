.class public final enum Lstarcom/snd/WebStreamPlayer$State;
.super Ljava/lang/Enum;
.source "WebStreamPlayer.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lstarcom/snd/WebStreamPlayer;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "State"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum",
        "<",
        "Lstarcom/snd/WebStreamPlayer$State;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lstarcom/snd/WebStreamPlayer$State;

.field public static final enum Pause:Lstarcom/snd/WebStreamPlayer$State;

.field public static final enum Playing:Lstarcom/snd/WebStreamPlayer$State;

.field public static final enum Preparing:Lstarcom/snd/WebStreamPlayer$State;

.field public static final enum Stopped:Lstarcom/snd/WebStreamPlayer$State;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .prologue
    const/4 v5, 0x3

    const/4 v4, 0x2

    const/4 v3, 0x1

    const/4 v2, 0x0

    .line 17
    new-instance v0, Lstarcom/snd/WebStreamPlayer$State;

    const-string/jumbo v1, "Preparing"

    invoke-direct {v0, v1, v2}, Lstarcom/snd/WebStreamPlayer$State;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lstarcom/snd/WebStreamPlayer$State;->Preparing:Lstarcom/snd/WebStreamPlayer$State;

    new-instance v0, Lstarcom/snd/WebStreamPlayer$State;

    const-string/jumbo v1, "Playing"

    invoke-direct {v0, v1, v3}, Lstarcom/snd/WebStreamPlayer$State;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lstarcom/snd/WebStreamPlayer$State;->Playing:Lstarcom/snd/WebStreamPlayer$State;

    new-instance v0, Lstarcom/snd/WebStreamPlayer$State;

    const-string/jumbo v1, "Pause"

    invoke-direct {v0, v1, v4}, Lstarcom/snd/WebStreamPlayer$State;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lstarcom/snd/WebStreamPlayer$State;->Pause:Lstarcom/snd/WebStreamPlayer$State;

    new-instance v0, Lstarcom/snd/WebStreamPlayer$State;

    const-string/jumbo v1, "Stopped"

    invoke-direct {v0, v1, v5}, Lstarcom/snd/WebStreamPlayer$State;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lstarcom/snd/WebStreamPlayer$State;->Stopped:Lstarcom/snd/WebStreamPlayer$State;

    const/4 v0, 0x4

    new-array v0, v0, [Lstarcom/snd/WebStreamPlayer$State;

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Preparing:Lstarcom/snd/WebStreamPlayer$State;

    aput-object v1, v0, v2

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Playing:Lstarcom/snd/WebStreamPlayer$State;

    aput-object v1, v0, v3

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Pause:Lstarcom/snd/WebStreamPlayer$State;

    aput-object v1, v0, v4

    sget-object v1, Lstarcom/snd/WebStreamPlayer$State;->Stopped:Lstarcom/snd/WebStreamPlayer$State;

    aput-object v1, v0, v5

    sput-object v0, Lstarcom/snd/WebStreamPlayer$State;->$VALUES:[Lstarcom/snd/WebStreamPlayer$State;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .prologue
    .line 17
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lstarcom/snd/WebStreamPlayer$State;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .prologue
    .line 17
    const-class v0, Lstarcom/snd/WebStreamPlayer$State;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebStreamPlayer$State;

    return-object v0
.end method

.method public static values()[Lstarcom/snd/WebStreamPlayer$State;
    .locals 1

    .prologue
    .line 17
    sget-object v0, Lstarcom/snd/WebStreamPlayer$State;->$VALUES:[Lstarcom/snd/WebStreamPlayer$State;

    invoke-virtual {v0}, [Lstarcom/snd/WebStreamPlayer$State;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lstarcom/snd/WebStreamPlayer$State;

    return-object v0
.end method
