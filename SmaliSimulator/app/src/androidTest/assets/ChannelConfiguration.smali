.class public final enum LChannelConfiguration;
.super Ljava/lang/Enum;
.source "ChannelConfiguration.java"


# static fields
.field private static final synthetic $VALUES:[LChannelConfiguration;

.field public static final enum CHANNEL_CONFIG_FIVE:LChannelConfiguration;

.field public static final enum CHANNEL_CONFIG_FIVE_PLUS_ONE:LChannelConfiguration;

.field public static final enum CHANNEL_CONFIG_MONO:LChannelConfiguration;

.field public static final enum CHANNEL_CONFIG_NONE:LChannelConfiguration;

.field public static final enum CHANNEL_CONFIG_SEVEN_PLUS_ONE:LChannelConfiguration;

.field public static final enum CHANNEL_CONFIG_STEREO:LChannelConfiguration;

.field public static final enum CHANNEL_CONFIG_STEREO_PLUS_CENTER:LChannelConfiguration;

.field public static final enum CHANNEL_CONFIG_STEREO_PLUS_CENTER_PLUS_REAR_MONO:LChannelConfiguration;

.field public static final enum CHANNEL_CONFIG_UNSUPPORTED:LChannelConfiguration;


# instance fields
.field private final chCount:I

.field private final descr:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 12

    .line 31
    new-instance v0, LChannelConfiguration;

    const-string v1, "CHANNEL_CONFIG_UNSUPPORTED"

    const/4 v2, 0x0

    const/4 v3, -0x1

    const-string v4, "invalid"

    invoke-direct {v0, v1, v2, v3, v4}, LChannelConfiguration;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LChannelConfiguration;->CHANNEL_CONFIG_UNSUPPORTED:LChannelConfiguration;

    .line 32
    new-instance v0, LChannelConfiguration;

    const-string v1, "CHANNEL_CONFIG_NONE"

    const/4 v3, 0x1

    const-string v4, "No channel"

    invoke-direct {v0, v1, v3, v2, v4}, LChannelConfiguration;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LChannelConfiguration;->CHANNEL_CONFIG_NONE:LChannelConfiguration;

    .line 33
    new-instance v0, LChannelConfiguration;

    const-string v1, "CHANNEL_CONFIG_MONO"

    const/4 v4, 0x2

    const-string v5, "Mono"

    invoke-direct {v0, v1, v4, v3, v5}, LChannelConfiguration;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LChannelConfiguration;->CHANNEL_CONFIG_MONO:LChannelConfiguration;

    .line 34
    new-instance v0, LChannelConfiguration;

    const-string v1, "CHANNEL_CONFIG_STEREO"

    const/4 v5, 0x3

    const-string v6, "Stereo"

    invoke-direct {v0, v1, v5, v4, v6}, LChannelConfiguration;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LChannelConfiguration;->CHANNEL_CONFIG_STEREO:LChannelConfiguration;

    .line 35
    new-instance v0, LChannelConfiguration;

    const-string v1, "CHANNEL_CONFIG_STEREO_PLUS_CENTER"

    const/4 v6, 0x4

    const-string v7, "Stereo+Center"

    invoke-direct {v0, v1, v6, v5, v7}, LChannelConfiguration;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LChannelConfiguration;->CHANNEL_CONFIG_STEREO_PLUS_CENTER:LChannelConfiguration;

    .line 36
    new-instance v0, LChannelConfiguration;

    const-string v1, "CHANNEL_CONFIG_STEREO_PLUS_CENTER_PLUS_REAR_MONO"

    const/4 v7, 0x5

    const-string v8, "Stereo+Center+Rear"

    invoke-direct {v0, v1, v7, v6, v8}, LChannelConfiguration;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LChannelConfiguration;->CHANNEL_CONFIG_STEREO_PLUS_CENTER_PLUS_REAR_MONO:LChannelConfiguration;

    .line 37
    new-instance v0, LChannelConfiguration;

    const-string v1, "CHANNEL_CONFIG_FIVE"

    const/4 v8, 0x6

    const-string v9, "Five channels"

    invoke-direct {v0, v1, v8, v7, v9}, LChannelConfiguration;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LChannelConfiguration;->CHANNEL_CONFIG_FIVE:LChannelConfiguration;

    .line 38
    new-instance v0, LChannelConfiguration;

    const-string v1, "CHANNEL_CONFIG_FIVE_PLUS_ONE"

    const/4 v9, 0x7

    const-string v10, "Five channels+LF"

    invoke-direct {v0, v1, v9, v8, v10}, LChannelConfiguration;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LChannelConfiguration;->CHANNEL_CONFIG_FIVE_PLUS_ONE:LChannelConfiguration;

    .line 39
    new-instance v0, LChannelConfiguration;

    const-string v1, "CHANNEL_CONFIG_SEVEN_PLUS_ONE"

    const/16 v10, 0x8

    const-string v11, "Seven channels+LF"

    invoke-direct {v0, v1, v10, v10, v11}, LChannelConfiguration;-><init>(Ljava/lang/String;IILjava/lang/String;)V

    sput-object v0, LChannelConfiguration;->CHANNEL_CONFIG_SEVEN_PLUS_ONE:LChannelConfiguration;

    .line 29
    const/16 v1, 0x9

    new-array v1, v1, [LChannelConfiguration;

    sget-object v11, LChannelConfiguration;->CHANNEL_CONFIG_UNSUPPORTED:LChannelConfiguration;

    aput-object v11, v1, v2

    sget-object v2, LChannelConfiguration;->CHANNEL_CONFIG_NONE:LChannelConfiguration;

    aput-object v2, v1, v3

    sget-object v2, LChannelConfiguration;->CHANNEL_CONFIG_MONO:LChannelConfiguration;

    aput-object v2, v1, v4

    sget-object v2, LChannelConfiguration;->CHANNEL_CONFIG_STEREO:LChannelConfiguration;

    aput-object v2, v1, v5

    sget-object v2, LChannelConfiguration;->CHANNEL_CONFIG_STEREO_PLUS_CENTER:LChannelConfiguration;

    aput-object v2, v1, v6

    sget-object v2, LChannelConfiguration;->CHANNEL_CONFIG_STEREO_PLUS_CENTER_PLUS_REAR_MONO:LChannelConfiguration;

    aput-object v2, v1, v7

    sget-object v2, LChannelConfiguration;->CHANNEL_CONFIG_FIVE:LChannelConfiguration;

    aput-object v2, v1, v8

    sget-object v2, LChannelConfiguration;->CHANNEL_CONFIG_FIVE_PLUS_ONE:LChannelConfiguration;

    aput-object v2, v1, v9

    aput-object v0, v1, v10

    sput-object v1, LChannelConfiguration;->$VALUES:[LChannelConfiguration;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;IILjava/lang/String;)V
    .locals 0
    .param p3, "chCount"    # I
    .param p4, "descr"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .line 79
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 80
    iput p3, p0, LChannelConfiguration;->chCount:I

    .line 81
    iput-object p4, p0, LChannelConfiguration;->descr:Ljava/lang/String;

    .line 82
    return-void
.end method

.method public static forInt(I)LChannelConfiguration;
    .locals 1
    .param p0, "i"    # I

    .line 43
    packed-switch p0, :pswitch_data_0

    .line 70
    sget-object v0, LChannelConfiguration;->CHANNEL_CONFIG_UNSUPPORTED:LChannelConfiguration;

    .local v0, "c":LChannelConfiguration;
    goto :goto_0

    .line 67
    .end local v0    # "c":LChannelConfiguration;
    :pswitch_0
    sget-object v0, LChannelConfiguration;->CHANNEL_CONFIG_SEVEN_PLUS_ONE:LChannelConfiguration;

    .line 68
    .restart local v0    # "c":LChannelConfiguration;
    goto :goto_0

    .line 63
    .end local v0    # "c":LChannelConfiguration;
    :pswitch_1
    sget-object v0, LChannelConfiguration;->CHANNEL_CONFIG_FIVE_PLUS_ONE:LChannelConfiguration;

    .line 64
    .restart local v0    # "c":LChannelConfiguration;
    goto :goto_0

    .line 60
    .end local v0    # "c":LChannelConfiguration;
    :pswitch_2
    sget-object v0, LChannelConfiguration;->CHANNEL_CONFIG_FIVE:LChannelConfiguration;

    .line 61
    .restart local v0    # "c":LChannelConfiguration;
    goto :goto_0

    .line 57
    .end local v0    # "c":LChannelConfiguration;
    :pswitch_3
    sget-object v0, LChannelConfiguration;->CHANNEL_CONFIG_STEREO_PLUS_CENTER_PLUS_REAR_MONO:LChannelConfiguration;

    .line 58
    .restart local v0    # "c":LChannelConfiguration;
    goto :goto_0

    .line 54
    .end local v0    # "c":LChannelConfiguration;
    :pswitch_4
    sget-object v0, LChannelConfiguration;->CHANNEL_CONFIG_STEREO_PLUS_CENTER:LChannelConfiguration;

    .line 55
    .restart local v0    # "c":LChannelConfiguration;
    goto :goto_0

    .line 51
    .end local v0    # "c":LChannelConfiguration;
    :pswitch_5
    sget-object v0, LChannelConfiguration;->CHANNEL_CONFIG_STEREO:LChannelConfiguration;

    .line 52
    .restart local v0    # "c":LChannelConfiguration;
    goto :goto_0

    .line 48
    .end local v0    # "c":LChannelConfiguration;
    :pswitch_6
    sget-object v0, LChannelConfiguration;->CHANNEL_CONFIG_MONO:LChannelConfiguration;

    .line 49
    .restart local v0    # "c":LChannelConfiguration;
    goto :goto_0

    .line 45
    .end local v0    # "c":LChannelConfiguration;
    :pswitch_7
    sget-object v0, LChannelConfiguration;->CHANNEL_CONFIG_NONE:LChannelConfiguration;

    .line 46
    .restart local v0    # "c":LChannelConfiguration;
    nop

    .line 73
    :goto_0
    return-object v0

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public static valueOf(Ljava/lang/String;)LChannelConfiguration;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .line 29
    const-class v0, LChannelConfiguration;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LChannelConfiguration;

    return-object v0
.end method

.method public static values()[LChannelConfiguration;
    .locals 1

    .line 29
    sget-object v0, LChannelConfiguration;->$VALUES:[LChannelConfiguration;

    invoke-virtual {v0}, [LChannelConfiguration;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LChannelConfiguration;

    return-object v0
.end method


# virtual methods
.method public getChannelCount()I
    .locals 1

    .line 88
    iget v0, p0, LChannelConfiguration;->chCount:I

    return v0
.end method

.method public getDescription()Ljava/lang/String;
    .locals 1

    .line 97
    iget-object v0, p0, LChannelConfiguration;->descr:Ljava/lang/String;

    return-object v0
.end method

.method public toString()Ljava/lang/String;
    .locals 1

    .line 109
    iget-object v0, p0, LChannelConfiguration;->descr:Ljava/lang/String;

    return-object v0
.end method