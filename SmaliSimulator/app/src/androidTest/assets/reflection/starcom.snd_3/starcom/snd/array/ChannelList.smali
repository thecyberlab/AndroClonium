.class public Lstarcom/snd/array/ChannelList;
.super Ljava/lang/Object;
.source "ChannelList.java"


# static fields
.field public static instance:Lstarcom/snd/array/ChannelList;


# instance fields
.field private channels_custom:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;"
        }
    .end annotation
.end field

.field private channels_default:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;"
        }
    .end annotation
.end field

.field private selectedChannel:I

.field private selectedDefault:Z


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 20
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private addMissingDefaults(Landroid/app/Activity;)Ljava/util/ArrayList;
    .locals 4
    .param p1, "activity"    # Landroid/app/Activity;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/app/Activity;",
            ")",
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;"
        }
    .end annotation

    .prologue
    .line 158
    invoke-static {p1}, Lstarcom/snd/array/ChannelList;->readDefaultChannels(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Lstarcom/snd/array/ChannelList;->getChannelsFromString(Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object v0

    .line 159
    .local v0, "channels_raw":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_0
    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lstarcom/snd/WebRadioChannel;

    .line 161
    .local v1, "curChannel":Lstarcom/snd/WebRadioChannel;
    iget-object v3, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_0

    iget-object v3, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 163
    .end local v1    # "curChannel":Lstarcom/snd/WebRadioChannel;
    :cond_1
    return-object v0
.end method

.method private static doWriteChannels(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;)V
    .locals 8
    .param p0, "activity"    # Landroid/app/Activity;
    .param p1, "toWrite"    # Ljava/lang/String;
    .param p2, "channels_filename"    # Ljava/lang/String;

    .prologue
    const/4 v6, 0x1

    const/4 v5, 0x0

    .line 265
    const-class v2, Lstarcom/snd/array/ChannelList;

    new-array v3, v6, [Ljava/lang/String;

    const-string/jumbo v4, "Write custom channels"

    aput-object v4, v3, v5

    invoke-static {v2, v3}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 266
    const/4 v2, 0x0

    :try_start_0
    invoke-virtual {p0, p2, v2}, Landroid/app/Activity;->openFileOutput(Ljava/lang/String;I)Ljava/io/FileOutputStream;
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1

    move-result-object v1

    .local v1, "os":Ljava/io/FileOutputStream;
    const/4 v3, 0x0

    .line 268
    :try_start_1
    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/io/FileOutputStream;->write([B)V

    .line 269
    invoke-virtual {v1}, Ljava/io/FileOutputStream;->flush()V

    .line 270
    invoke-virtual {v1}, Ljava/io/FileOutputStream;->close()V
    :try_end_1
    .catch Ljava/lang/Throwable; {:try_start_1 .. :try_end_1} :catch_2
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 271
    if-eqz v1, :cond_0

    if-eqz v3, :cond_1

    :try_start_2
    invoke-virtual {v1}, Ljava/io/FileOutputStream;->close()V
    :try_end_2
    .catch Ljava/lang/Throwable; {:try_start_2 .. :try_end_2} :catch_0
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_1

    .line 277
    .end local v1    # "os":Ljava/io/FileOutputStream;
    :cond_0
    :goto_0
    return-void

    .line 271
    .restart local v1    # "os":Ljava/io/FileOutputStream;
    :catch_0
    move-exception v2

    :try_start_3
    invoke-virtual {v3, v2}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V
    :try_end_3
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_1

    goto :goto_0

    .line 272
    .end local v1    # "os":Ljava/io/FileOutputStream;
    :catch_1
    move-exception v0

    .line 274
    .local v0, "e":Ljava/io/IOException;
    const-class v2, Lstarcom/snd/array/ChannelList;

    new-array v3, v6, [Ljava/lang/String;

    const-string/jumbo v4, "Writing channels"

    aput-object v4, v3, v5

    invoke-static {v2, v0, v3}, Lstarcom/debug/LoggingSystem;->severe(Ljava/lang/Class;Ljava/lang/Exception;[Ljava/lang/String;)V

    .line 275
    invoke-virtual {p0}, Landroid/app/Activity;->getApplicationContext()Landroid/content/Context;

    move-result-object v2

    const-string/jumbo v3, "Error writing channels!"

    invoke-static {v2, v3, v6}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 271
    .end local v0    # "e":Ljava/io/IOException;
    .restart local v1    # "os":Ljava/io/FileOutputStream;
    :cond_1
    :try_start_4
    invoke-virtual {v1}, Ljava/io/FileOutputStream;->close()V
    :try_end_4
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_1

    goto :goto_0

    .line 266
    :catch_2
    move-exception v2

    :try_start_5
    throw v2
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_0

    .line 271
    :catchall_0
    move-exception v3

    move-object v7, v3

    move-object v3, v2

    move-object v2, v7

    :goto_1
    if-eqz v1, :cond_2

    if-eqz v3, :cond_3

    :try_start_6
    invoke-virtual {v1}, Ljava/io/FileOutputStream;->close()V
    :try_end_6
    .catch Ljava/lang/Throwable; {:try_start_6 .. :try_end_6} :catch_3
    .catch Ljava/io/IOException; {:try_start_6 .. :try_end_6} :catch_1

    :cond_2
    :goto_2
    :try_start_7
    throw v2

    :catch_3
    move-exception v4

    invoke-virtual {v3, v4}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_2

    :cond_3
    invoke-virtual {v1}, Ljava/io/FileOutputStream;->close()V
    :try_end_7
    .catch Ljava/io/IOException; {:try_start_7 .. :try_end_7} :catch_1

    goto :goto_2

    :catchall_1
    move-exception v2

    goto :goto_1
.end method

.method private static getChannelsFromString(Ljava/lang/String;)Ljava/util/ArrayList;
    .locals 19
    .param p0, "txt"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;"
        }
    .end annotation

    .prologue
    .line 209
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 210
    .local v1, "arrayAdapter":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    const/4 v9, 0x0

    .line 211
    .local v9, "lastName":Ljava/lang/String;
    :try_start_0
    new-instance v2, Ljava/io/BufferedReader;

    new-instance v12, Ljava/io/StringReader;

    move-object/from16 v0, p0

    invoke-direct {v12, v0}, Ljava/io/StringReader;-><init>(Ljava/lang/String;)V

    invoke-direct {v2, v12}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1

    .local v2, "br":Ljava/io/BufferedReader;
    const/4 v13, 0x0

    .line 213
    :goto_0
    :try_start_1
    invoke-virtual {v2}, Ljava/io/BufferedReader;->ready()Z

    move-result v12

    if-eqz v12, :cond_0

    .line 215
    invoke-virtual {v2}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;
    :try_end_1
    .catch Ljava/lang/Throwable; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    move-result-object v11

    .line 216
    .local v11, "s":Ljava/lang/String;
    if-nez v11, :cond_2

    .line 241
    .end local v11    # "s":Ljava/lang/String;
    :cond_0
    if-eqz v2, :cond_1

    if-eqz v13, :cond_b

    :try_start_2
    invoke-virtual {v2}, Ljava/io/BufferedReader;->close()V
    :try_end_2
    .catch Ljava/lang/Throwable; {:try_start_2 .. :try_end_2} :catch_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_1

    .line 246
    .end local v2    # "br":Ljava/io/BufferedReader;
    :cond_1
    :goto_1
    return-object v1

    .line 217
    .restart local v2    # "br":Ljava/io/BufferedReader;
    .restart local v11    # "s":Ljava/lang/String;
    :cond_2
    :try_start_3
    const-string/jumbo v12, "name="

    invoke-virtual {v11, v12}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v5

    .line 218
    .local v5, "hasName":Z
    const-string/jumbo v12, "#name="

    invoke-virtual {v11, v12}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    .line 219
    .local v6, "hasNameCom":Z
    const-string/jumbo v12, "url="

    invoke-virtual {v11, v12}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    .line 220
    .local v7, "hasUri":Z
    const-string/jumbo v12, "#url="

    invoke-virtual {v11, v12}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v8

    .line 221
    .local v8, "hasUriCom":Z
    if-nez v5, :cond_3

    if-eqz v6, :cond_5

    .line 223
    :cond_3
    if-eqz v6, :cond_4

    const-string/jumbo v12, "#name="

    invoke-virtual {v12}, Ljava/lang/String;->length()I

    move-result v12

    invoke-virtual {v11, v12}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v9

    goto :goto_0

    .line 224
    :cond_4
    const-string/jumbo v12, "name="

    invoke-virtual {v12}, Ljava/lang/String;->length()I

    move-result v12

    invoke-virtual {v11, v12}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v9

    goto :goto_0

    .line 226
    :cond_5
    if-nez v7, :cond_6

    if-eqz v8, :cond_9

    :cond_6
    if-eqz v9, :cond_9

    .line 228
    const-class v12, Lstarcom/snd/WebRadio;

    const/4 v14, 0x2

    new-array v14, v14, [Ljava/lang/String;

    const/4 v15, 0x0

    const-string/jumbo v16, "Appending channel: "

    aput-object v16, v14, v15

    const/4 v15, 0x1

    aput-object v9, v14, v15

    invoke-static {v12, v14}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 229
    const-string/jumbo v12, "url="

    invoke-virtual {v12}, Ljava/lang/String;->length()I

    move-result v12

    invoke-virtual {v11, v12}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v10

    .line 230
    .local v10, "newUri":Ljava/lang/String;
    if-eqz v8, :cond_7

    const-string/jumbo v12, "#url="

    invoke-virtual {v12}, Ljava/lang/String;->length()I

    move-result v12

    invoke-virtual {v11, v12}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v10

    .line 231
    :cond_7
    new-instance v3, Lstarcom/snd/WebRadioChannel;

    invoke-direct {v3, v9, v10}, Lstarcom/snd/WebRadioChannel;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 232
    .local v3, "curChannel":Lstarcom/snd/WebRadioChannel;
    if-eqz v8, :cond_8

    const/4 v12, 0x0

    invoke-virtual {v3, v12}, Lstarcom/snd/WebRadioChannel;->setSelected(Z)V

    .line 233
    :cond_8
    invoke-virtual {v1, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 234
    const/4 v9, 0x0

    .line 235
    goto/16 :goto_0

    .line 238
    .end local v3    # "curChannel":Lstarcom/snd/WebRadioChannel;
    .end local v10    # "newUri":Ljava/lang/String;
    :cond_9
    const-class v12, Lstarcom/snd/array/ChannelList;

    const/4 v14, 0x1

    new-array v14, v14, [Ljava/lang/String;

    const/4 v15, 0x0

    new-instance v16, Ljava/lang/StringBuilder;

    invoke-direct/range {v16 .. v16}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v17, "Unknown Line: "

    invoke-virtual/range {v16 .. v17}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v0, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    aput-object v16, v14, v15

    invoke-static {v12, v14}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V
    :try_end_3
    .catch Ljava/lang/Throwable; {:try_start_3 .. :try_end_3} :catch_0
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    goto/16 :goto_0

    .line 211
    .end local v5    # "hasName":Z
    .end local v6    # "hasNameCom":Z
    .end local v7    # "hasUri":Z
    .end local v8    # "hasUriCom":Z
    .end local v11    # "s":Ljava/lang/String;
    :catch_0
    move-exception v12

    :try_start_4
    throw v12
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_0

    .line 241
    :catchall_0
    move-exception v13

    move-object/from16 v18, v13

    move-object v13, v12

    move-object/from16 v12, v18

    :goto_2
    if-eqz v2, :cond_a

    if-eqz v13, :cond_c

    :try_start_5
    invoke-virtual {v2}, Ljava/io/BufferedReader;->close()V
    :try_end_5
    .catch Ljava/lang/Throwable; {:try_start_5 .. :try_end_5} :catch_3
    .catch Ljava/io/IOException; {:try_start_5 .. :try_end_5} :catch_1

    :cond_a
    :goto_3
    :try_start_6
    throw v12
    :try_end_6
    .catch Ljava/io/IOException; {:try_start_6 .. :try_end_6} :catch_1

    .line 242
    .end local v2    # "br":Ljava/io/BufferedReader;
    :catch_1
    move-exception v4

    .line 244
    .local v4, "e":Ljava/io/IOException;
    const-class v12, Lstarcom/snd/array/ChannelList;

    const/4 v13, 0x1

    new-array v13, v13, [Ljava/lang/String;

    const/4 v14, 0x0

    const-string/jumbo v15, "Get channels from String"

    aput-object v15, v13, v14

    invoke-static {v12, v4, v13}, Lstarcom/debug/LoggingSystem;->severe(Ljava/lang/Class;Ljava/lang/Exception;[Ljava/lang/String;)V

    goto/16 :goto_1

    .line 241
    .end local v4    # "e":Ljava/io/IOException;
    .restart local v2    # "br":Ljava/io/BufferedReader;
    :catch_2
    move-exception v12

    :try_start_7
    invoke-virtual {v13, v12}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto/16 :goto_1

    :cond_b
    invoke-virtual {v2}, Ljava/io/BufferedReader;->close()V

    goto/16 :goto_1

    :catch_3
    move-exception v14

    invoke-virtual {v13, v14}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_3

    :cond_c
    invoke-virtual {v2}, Ljava/io/BufferedReader;->close()V
    :try_end_7
    .catch Ljava/io/IOException; {:try_start_7 .. :try_end_7} :catch_1

    goto :goto_3

    :catchall_1
    move-exception v12

    goto :goto_2
.end method

.method public static getInstance()Lstarcom/snd/array/ChannelList;
    .locals 1

    .prologue
    .line 38
    sget-object v0, Lstarcom/snd/array/ChannelList;->instance:Lstarcom/snd/array/ChannelList;

    return-object v0
.end method

.method private static getStringFromChannels(Ljava/util/ArrayList;)Ljava/lang/String;
    .locals 10
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;)",
            "Ljava/lang/String;"
        }
    .end annotation

    .prologue
    .line 251
    .local p0, "adapter":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    .line 252
    .local v3, "sb":Ljava/lang/StringBuilder;
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    invoke-virtual {p0}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-ge v1, v4, :cond_1

    .line 254
    invoke-virtual {p0, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebRadioChannel;

    .line 255
    .local v0, "curChannel":Lstarcom/snd/WebRadioChannel;
    const-string/jumbo v2, "#"

    .line 256
    .local v2, "raute":Ljava/lang/String;
    const/4 v6, 0x0

    const/16 v8, 0x15

    invoke-static {v8, v0, v6}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/Boolean;

    invoke-virtual {v4}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v4

    if-eqz v4, :cond_0

    const-string/jumbo v2, ""

    .line 257
    :cond_0
    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string/jumbo v5, "name="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const/4 v6, 0x0

    const/16 v8, 0x16

    invoke-static {v8, v0, v6}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string/jumbo v5, "\n"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 258
    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string/jumbo v5, "url="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const/4 v6, 0x0

    const/16 v8, 0x17

    invoke-static {v8, v0, v6}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string/jumbo v5, "\n"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 252
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 260
    .end local v0    # "curChannel":Lstarcom/snd/WebRadioChannel;
    .end local v2    # "raute":Ljava/lang/String;
    :cond_1
    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    return-object v4
.end method

.method public static init(Landroid/app/Activity;)V
    .locals 3
    .param p0, "activity"    # Landroid/app/Activity;

    .prologue
    .line 43
    sget-object v1, Lstarcom/snd/array/ChannelList;->instance:Lstarcom/snd/array/ChannelList;

    if-nez v1, :cond_0

    .line 45
    new-instance v1, Lstarcom/snd/array/ChannelList;

    invoke-direct {v1}, Lstarcom/snd/array/ChannelList;-><init>()V

    sput-object v1, Lstarcom/snd/array/ChannelList;->instance:Lstarcom/snd/array/ChannelList;

    .line 46
    const-string/jumbo v1, "channel_list.properties"

    invoke-static {p0, v1}, Lstarcom/snd/array/ChannelList;->readChannels(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 47
    .local v0, "channelsTxt":Ljava/lang/String;
    sget-object v1, Lstarcom/snd/array/ChannelList;->instance:Lstarcom/snd/array/ChannelList;

    invoke-static {v0}, Lstarcom/snd/array/ChannelList;->getChannelsFromString(Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object v2

    iput-object v2, v1, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    .line 48
    const-string/jumbo v1, "channel_list_default.properties"

    invoke-static {p0, v1}, Lstarcom/snd/array/ChannelList;->readChannels(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 49
    sget-object v1, Lstarcom/snd/array/ChannelList;->instance:Lstarcom/snd/array/ChannelList;

    invoke-static {v0}, Lstarcom/snd/array/ChannelList;->getChannelsFromString(Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object v2

    iput-object v2, v1, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    .line 50
    sget-object v1, Lstarcom/snd/array/ChannelList;->instance:Lstarcom/snd/array/ChannelList;

    invoke-virtual {v1, p0}, Lstarcom/snd/array/ChannelList;->checkVersion(Landroid/app/Activity;)V

    .line 52
    .end local v0    # "channelsTxt":Ljava/lang/String;
    :cond_0
    return-void
.end method

.method private manipulateCustomAndDefault(Ljava/util/ArrayList;)V
    .locals 9
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 142
    .local p1, "channels_raw":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    invoke-virtual {p1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v4

    :cond_0
    :goto_0
    invoke-interface {v4}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_2

    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebRadioChannel;

    .line 144
    .local v0, "curChannel":Lstarcom/snd/WebRadioChannel;
    iget-object v3, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-virtual {v3, v0}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_1

    .line 146
    iget-object v3, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-virtual {v3, v0}, Ljava/util/ArrayList;->indexOf(Ljava/lang/Object;)I

    move-result v2

    .line 147
    .local v2, "index":I
    iget-object v3, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lstarcom/snd/WebRadioChannel;

    .line 148
    .local v1, "deprecatedChannel":Lstarcom/snd/WebRadioChannel;
    iget-object v3, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-virtual {v3, v0}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 149
    const/4 v5, 0x0

    const/16 v7, 0x18

    invoke-static {v7, v1, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Boolean;

    invoke-virtual {v3}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v3

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    invoke-static {v3}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v6

    const/4 v8, 0x0

    aput-object v6, v5, v8

    const/16 v7, 0x19

    invoke-static {v7, v0, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 151
    .end local v1    # "deprecatedChannel":Lstarcom/snd/WebRadioChannel;
    .end local v2    # "index":I
    :cond_1
    iget-object v3, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    invoke-virtual {v3, v0}, Ljava/util/ArrayList;->indexOf(Ljava/lang/Object;)I

    move-result v2

    .line 152
    .restart local v2    # "index":I
    if-gez v2, :cond_0

    iget-object v3, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    invoke-virtual {v3, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lstarcom/snd/WebRadioChannel;

    const/4 v5, 0x0

    const/16 v7, 0x1a

    invoke-static {v7, v3, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Boolean;

    invoke-virtual {v3}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v3

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    invoke-static {v3}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v6

    const/4 v8, 0x0

    aput-object v6, v5, v8

    const/16 v7, 0x1b

    invoke-static {v7, v0, v5}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    .line 154
    .end local v0    # "curChannel":Lstarcom/snd/WebRadioChannel;
    .end local v2    # "index":I
    :cond_2
    return-void
.end method

.method private static readChannels(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/String;
    .locals 13
    .param p0, "activity"    # Landroid/app/Activity;
    .param p1, "channelFile"    # Ljava/lang/String;

    .prologue
    const/4 v8, 0x0

    .line 171
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    .line 172
    .local v4, "sb":Ljava/lang/StringBuilder;
    :try_start_0
    invoke-virtual {p0, p1}, Landroid/app/Activity;->openFileInput(Ljava/lang/String;)Ljava/io/FileInputStream;
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_3

    move-result-object v2

    .local v2, "is":Ljava/io/FileInputStream;
    const/4 v7, 0x0

    .line 173
    :try_start_1
    new-instance v5, Ljava/io/InputStreamReader;

    invoke-direct {v5, v2}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;)V
    :try_end_1
    .catch Ljava/lang/Throwable; {:try_start_1 .. :try_end_1} :catch_2
    .catchall {:try_start_1 .. :try_end_1} :catchall_4

    .line 172
    .local v5, "sr":Ljava/io/InputStreamReader;
    const/4 v9, 0x0

    .line 174
    :try_start_2
    new-instance v0, Ljava/io/BufferedReader;

    invoke-direct {v0, v5}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V
    :try_end_2
    .catch Ljava/lang/Throwable; {:try_start_2 .. :try_end_2} :catch_1
    .catchall {:try_start_2 .. :try_end_2} :catchall_3

    .line 172
    .local v0, "br":Ljava/io/BufferedReader;
    const/4 v10, 0x0

    .line 176
    :goto_0
    :try_start_3
    invoke-virtual {v0}, Ljava/io/BufferedReader;->ready()Z

    move-result v6

    if-eqz v6, :cond_4

    .line 178
    invoke-virtual {v0}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v3

    .line 179
    .local v3, "s":Ljava/lang/String;
    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string/jumbo v11, "\n"

    invoke-virtual {v6, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    :try_end_3
    .catch Ljava/lang/Throwable; {:try_start_3 .. :try_end_3} :catch_0
    .catchall {:try_start_3 .. :try_end_3} :catchall_5

    goto :goto_0

    .line 172
    .end local v3    # "s":Ljava/lang/String;
    :catch_0
    move-exception v6

    :try_start_4
    throw v6
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_0

    .line 182
    :catchall_0
    move-exception v7

    move-object v12, v7

    move-object v7, v6

    move-object v6, v12

    :goto_1
    if-eqz v0, :cond_0

    if-eqz v7, :cond_a

    :try_start_5
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V
    :try_end_5
    .catch Ljava/lang/Throwable; {:try_start_5 .. :try_end_5} :catch_7
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    :cond_0
    :goto_2
    :try_start_6
    throw v6
    :try_end_6
    .catch Ljava/lang/Throwable; {:try_start_6 .. :try_end_6} :catch_1
    .catchall {:try_start_6 .. :try_end_6} :catchall_3

    .line 172
    .end local v0    # "br":Ljava/io/BufferedReader;
    :catch_1
    move-exception v6

    :try_start_7
    throw v6
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_1

    .line 182
    :catchall_1
    move-exception v7

    move-object v12, v7

    move-object v7, v6

    move-object v6, v12

    :goto_3
    if-eqz v5, :cond_1

    if-eqz v7, :cond_b

    :try_start_8
    invoke-virtual {v5}, Ljava/io/InputStreamReader;->close()V
    :try_end_8
    .catch Ljava/lang/Throwable; {:try_start_8 .. :try_end_8} :catch_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_4

    :cond_1
    :goto_4
    :try_start_9
    throw v6
    :try_end_9
    .catch Ljava/lang/Throwable; {:try_start_9 .. :try_end_9} :catch_2
    .catchall {:try_start_9 .. :try_end_9} :catchall_4

    .line 172
    .end local v5    # "sr":Ljava/io/InputStreamReader;
    :catch_2
    move-exception v6

    :try_start_a
    throw v6
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_2

    .line 182
    :catchall_2
    move-exception v7

    move-object v8, v6

    move-object v6, v7

    :goto_5
    if-eqz v2, :cond_2

    if-eqz v8, :cond_c

    :try_start_b
    invoke-virtual {v2}, Ljava/io/FileInputStream;->close()V
    :try_end_b
    .catch Ljava/lang/Throwable; {:try_start_b .. :try_end_b} :catch_9
    .catch Ljava/io/IOException; {:try_start_b .. :try_end_b} :catch_3

    :cond_2
    :goto_6
    :try_start_c
    throw v6
    :try_end_c
    .catch Ljava/io/IOException; {:try_start_c .. :try_end_c} :catch_3

    .line 183
    .end local v2    # "is":Ljava/io/FileInputStream;
    :catch_3
    move-exception v1

    .line 185
    .local v1, "e":Ljava/io/IOException;
    const-class v6, Lstarcom/snd/array/ChannelList;

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/String;

    const/4 v8, 0x0

    const-string/jumbo v9, "Reading channels"

    aput-object v9, v7, v8

    invoke-static {v6, v1, v7}, Lstarcom/debug/LoggingSystem;->severe(Ljava/lang/Class;Ljava/lang/Exception;[Ljava/lang/String;)V

    .line 186
    const-string/jumbo v6, ""

    .end local v1    # "e":Ljava/io/IOException;
    :cond_3
    :goto_7
    return-object v6

    .line 181
    .restart local v0    # "br":Ljava/io/BufferedReader;
    .restart local v2    # "is":Ljava/io/FileInputStream;
    .restart local v5    # "sr":Ljava/io/InputStreamReader;
    :cond_4
    :try_start_d
    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_d
    .catch Ljava/lang/Throwable; {:try_start_d .. :try_end_d} :catch_0
    .catchall {:try_start_d .. :try_end_d} :catchall_5

    move-result-object v6

    .line 182
    if-eqz v0, :cond_5

    if-eqz v8, :cond_7

    :try_start_e
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V
    :try_end_e
    .catch Ljava/lang/Throwable; {:try_start_e .. :try_end_e} :catch_5
    .catchall {:try_start_e .. :try_end_e} :catchall_3

    :cond_5
    :goto_8
    if-eqz v5, :cond_6

    if-eqz v8, :cond_8

    :try_start_f
    invoke-virtual {v5}, Ljava/io/InputStreamReader;->close()V
    :try_end_f
    .catch Ljava/lang/Throwable; {:try_start_f .. :try_end_f} :catch_6
    .catchall {:try_start_f .. :try_end_f} :catchall_4

    :cond_6
    :goto_9
    if-eqz v2, :cond_3

    if-eqz v8, :cond_9

    :try_start_10
    invoke-virtual {v2}, Ljava/io/FileInputStream;->close()V
    :try_end_10
    .catch Ljava/lang/Throwable; {:try_start_10 .. :try_end_10} :catch_4
    .catch Ljava/io/IOException; {:try_start_10 .. :try_end_10} :catch_3

    goto :goto_7

    :catch_4
    move-exception v8

    :try_start_11
    invoke-virtual {v7, v8}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V
    :try_end_11
    .catch Ljava/io/IOException; {:try_start_11 .. :try_end_11} :catch_3

    goto :goto_7

    :catch_5
    move-exception v11

    :try_start_12
    invoke-virtual {v10, v11}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_8

    .end local v0    # "br":Ljava/io/BufferedReader;
    :catchall_3
    move-exception v6

    move-object v7, v8

    goto :goto_3

    .restart local v0    # "br":Ljava/io/BufferedReader;
    :cond_7
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V
    :try_end_12
    .catch Ljava/lang/Throwable; {:try_start_12 .. :try_end_12} :catch_1
    .catchall {:try_start_12 .. :try_end_12} :catchall_3

    goto :goto_8

    :catch_6
    move-exception v10

    :try_start_13
    invoke-virtual {v9, v10}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_9

    .end local v0    # "br":Ljava/io/BufferedReader;
    .end local v5    # "sr":Ljava/io/InputStreamReader;
    :catchall_4
    move-exception v6

    goto :goto_5

    .restart local v0    # "br":Ljava/io/BufferedReader;
    .restart local v5    # "sr":Ljava/io/InputStreamReader;
    :cond_8
    invoke-virtual {v5}, Ljava/io/InputStreamReader;->close()V
    :try_end_13
    .catch Ljava/lang/Throwable; {:try_start_13 .. :try_end_13} :catch_2
    .catchall {:try_start_13 .. :try_end_13} :catchall_4

    goto :goto_9

    :cond_9
    :try_start_14
    invoke-virtual {v2}, Ljava/io/FileInputStream;->close()V
    :try_end_14
    .catch Ljava/io/IOException; {:try_start_14 .. :try_end_14} :catch_3

    goto :goto_7

    :catch_7
    move-exception v9

    :try_start_15
    invoke-virtual {v7, v9}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_2

    :cond_a
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V
    :try_end_15
    .catch Ljava/lang/Throwable; {:try_start_15 .. :try_end_15} :catch_1
    .catchall {:try_start_15 .. :try_end_15} :catchall_3

    goto :goto_2

    .end local v0    # "br":Ljava/io/BufferedReader;
    :catch_8
    move-exception v9

    :try_start_16
    invoke-virtual {v7, v9}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_4

    :cond_b
    invoke-virtual {v5}, Ljava/io/InputStreamReader;->close()V
    :try_end_16
    .catch Ljava/lang/Throwable; {:try_start_16 .. :try_end_16} :catch_2
    .catchall {:try_start_16 .. :try_end_16} :catchall_4

    goto :goto_4

    .end local v5    # "sr":Ljava/io/InputStreamReader;
    :catch_9
    move-exception v7

    :try_start_17
    invoke-virtual {v8, v7}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_6

    :cond_c
    invoke-virtual {v2}, Ljava/io/FileInputStream;->close()V
    :try_end_17
    .catch Ljava/io/IOException; {:try_start_17 .. :try_end_17} :catch_3

    goto :goto_6

    .restart local v0    # "br":Ljava/io/BufferedReader;
    .restart local v5    # "sr":Ljava/io/InputStreamReader;
    :catchall_5
    move-exception v6

    move-object v7, v8

    goto/16 :goto_1
.end method

.method private static readDefaultChannels(Landroid/content/Context;)Ljava/lang/String;
    .locals 5
    .param p0, "context"    # Landroid/content/Context;

    .prologue
    .line 201
    const v0, 0x7f040001

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v3, 0x0

    aput-object p0, v1, v3

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const/4 v4, 0x1

    aput-object v2, v1, v4

    const/16 v3, 0x1c

    const/4 v4, 0x0

    invoke-static {v3, v4, v1}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    return-object v0
.end method


# virtual methods
.method checkVersion(Landroid/app/Activity;)V
    .locals 12
    .param p1, "activity"    # Landroid/app/Activity;

    .prologue
    const/4 v3, 0x1

    const/4 v7, 0x0

    .line 110
    const-string/jumbo v1, ""

    .line 113
    .local v1, "curAppVersion":Ljava/lang/String;
    :try_start_0
    invoke-virtual {p1}, Landroid/app/Activity;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v8

    invoke-virtual {p1}, Landroid/app/Activity;->getPackageName()Ljava/lang/String;

    move-result-object v9

    const/4 v10, 0x0

    invoke-virtual {v8, v9, v10}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object v8

    iget-object v1, v8, Landroid/content/pm/PackageInfo;->versionName:Ljava/lang/String;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 119
    :goto_0
    invoke-virtual {p1, v7}, Landroid/app/Activity;->getPreferences(I)Landroid/content/SharedPreferences;

    move-result-object v4

    .line 120
    .local v4, "pref":Landroid/content/SharedPreferences;
    const-string/jumbo v8, "starcom.snd.versiondate"

    const-string/jumbo v9, ""

    invoke-interface {v4, v8, v9}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    .line 121
    .local v6, "storedAppVersion":Ljava/lang/String;
    const-class v8, Lstarcom/snd/array/ChannelList;

    new-array v9, v3, [Ljava/lang/String;

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string/jumbo v11, "Version-check: stored="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string/jumbo v11, " cur="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    aput-object v10, v9, v7

    invoke-static {v8, v9}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 122
    invoke-virtual {v6, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-nez v8, :cond_3

    .line 124
    .local v3, "isNewVersion":Z
    :goto_1
    if-nez v3, :cond_0

    iget-object v7, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    invoke-virtual {v7}, Ljava/util/ArrayList;->size()I

    move-result v7

    if-nez v7, :cond_2

    .line 126
    :cond_0
    invoke-direct {p0, p1}, Lstarcom/snd/array/ChannelList;->addMissingDefaults(Landroid/app/Activity;)Ljava/util/ArrayList;

    move-result-object v0

    .line 127
    .local v0, "channels_raw":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    if-eqz v3, :cond_1

    .line 129
    invoke-direct {p0, v0}, Lstarcom/snd/array/ChannelList;->manipulateCustomAndDefault(Ljava/util/ArrayList;)V

    .line 130
    const-string/jumbo v7, "channel_list.properties"

    invoke-virtual {p0, p1, v7}, Lstarcom/snd/array/ChannelList;->writeChannels(Landroid/app/Activity;Ljava/lang/String;)V

    .line 131
    invoke-interface {v4}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v5

    .line 132
    .local v5, "sharedEditor":Landroid/content/SharedPreferences$Editor;
    const-string/jumbo v7, "starcom.snd.versiondate"

    invoke-interface {v5, v7, v1}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 133
    invoke-interface {v5}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 135
    .end local v5    # "sharedEditor":Landroid/content/SharedPreferences$Editor;
    :cond_1
    iput-object v0, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    .line 136
    const-string/jumbo v7, "channel_list_default.properties"

    invoke-virtual {p0, p1, v7}, Lstarcom/snd/array/ChannelList;->writeChannels(Landroid/app/Activity;Ljava/lang/String;)V

    .line 138
    .end local v0    # "channels_raw":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    :cond_2
    return-void

    .line 115
    .end local v3    # "isNewVersion":Z
    .end local v4    # "pref":Landroid/content/SharedPreferences;
    .end local v6    # "storedAppVersion":Ljava/lang/String;
    :catch_0
    move-exception v2

    .line 117
    .local v2, "e":Ljava/lang/Exception;
    const-class v8, Lstarcom/snd/array/ChannelList;

    new-array v9, v3, [Ljava/lang/String;

    const-string/jumbo v10, "Error while getting app version!!!!"

    aput-object v10, v9, v7

    invoke-static {v8, v2, v9}, Lstarcom/debug/LoggingSystem;->severe(Ljava/lang/Class;Ljava/lang/Exception;[Ljava/lang/String;)V

    goto :goto_0

    .end local v2    # "e":Ljava/lang/Exception;
    .restart local v4    # "pref":Landroid/content/SharedPreferences;
    .restart local v6    # "storedAppVersion":Ljava/lang/String;
    :cond_3
    move v3, v7

    .line 122
    goto :goto_1
.end method

.method public createSelectedChannelList()Ljava/util/ArrayList;
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;"
        }
    .end annotation

    .prologue
    .line 59
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 60
    .local v1, "list":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    iget-object v2, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_0
    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebRadioChannel;

    .line 62
    .local v0, "curChannel":Lstarcom/snd/WebRadioChannel;
    const/4 v4, 0x0

    const/16 v6, 0x1d

    invoke-static {v6, v0, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Boolean;

    invoke-virtual {v3}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 64
    .end local v0    # "curChannel":Lstarcom/snd/WebRadioChannel;
    :cond_1
    iget-object v2, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_2
    :goto_1
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_3

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebRadioChannel;

    .line 66
    .restart local v0    # "curChannel":Lstarcom/snd/WebRadioChannel;
    const/4 v4, 0x0

    const/16 v6, 0x1e

    invoke-static {v6, v0, v4}, Lcom/apireflectionmanager/ApiReflection;->obfuscate(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Boolean;

    invoke-virtual {v3}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v3

    if-eqz v3, :cond_2

    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_1

    .line 68
    .end local v0    # "curChannel":Lstarcom/snd/WebRadioChannel;
    :cond_3
    return-object v1
.end method

.method public getCustomChannelList()Ljava/util/ArrayList;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;"
        }
    .end annotation

    .prologue
    .line 54
    iget-object v0, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    return-object v0
.end method

.method public getDefaultChannelList()Ljava/util/ArrayList;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;"
        }
    .end annotation

    .prologue
    .line 55
    iget-object v0, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    return-object v0
.end method

.method public getSelectedChannel()Lstarcom/snd/WebRadioChannel;
    .locals 2

    .prologue
    .line 97
    iget v0, p0, Lstarcom/snd/array/ChannelList;->selectedChannel:I

    if-gez v0, :cond_0

    const/4 v0, 0x0

    .line 99
    :goto_0
    return-object v0

    .line 98
    :cond_0
    iget-boolean v0, p0, Lstarcom/snd/array/ChannelList;->selectedDefault:Z

    if-eqz v0, :cond_1

    iget-object v0, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    iget v1, p0, Lstarcom/snd/array/ChannelList;->selectedChannel:I

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebRadioChannel;

    goto :goto_0

    .line 99
    :cond_1
    iget-object v0, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    iget v1, p0, Lstarcom/snd/array/ChannelList;->selectedChannel:I

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lstarcom/snd/WebRadioChannel;

    goto :goto_0
.end method

.method public listViewCreateChannelList()Ljava/util/ArrayList;
    .locals 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/ArrayList",
            "<",
            "Lstarcom/snd/WebRadioChannel;",
            ">;"
        }
    .end annotation

    .prologue
    .line 73
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 74
    .local v0, "list":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lstarcom/snd/WebRadioChannel;>;"
    new-instance v1, Lstarcom/snd/WebRadioChannel;

    const-string/jumbo v2, "2131034117"

    const-string/jumbo v3, "###/*-! SEP /*!-###"

    invoke-direct {v1, v2, v3}, Lstarcom/snd/WebRadioChannel;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 75
    iget-object v1, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    .line 76
    new-instance v1, Lstarcom/snd/WebRadioChannel;

    const-string/jumbo v2, "2131034122"

    const-string/jumbo v3, "###/*-! SEP /*!-###"

    invoke-direct {v1, v2, v3}, Lstarcom/snd/WebRadioChannel;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 77
    iget-object v1, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    invoke-virtual {v0, v1}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    .line 78
    return-object v0
.end method

.method public listViewGetArrayPos(I)I
    .locals 2
    .param p1, "pos"    # I

    .prologue
    const/4 v0, -0x1

    .line 89
    if-nez p1, :cond_1

    .line 92
    :cond_0
    :goto_0
    return v0

    .line 90
    :cond_1
    iget-object v1, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    add-int/lit8 v1, v1, 0x1

    if-eq p1, v1, :cond_0

    .line 91
    iget-object v0, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-gt p1, v0, :cond_2

    add-int/lit8 v0, p1, -0x1

    goto :goto_0

    .line 92
    :cond_2
    iget-object v0, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    add-int/lit8 v0, v0, 0x1

    add-int/lit8 v0, v0, 0x1

    sub-int v0, p1, v0

    goto :goto_0
.end method

.method public listViewIsDefault(I)Z
    .locals 1
    .param p1, "pos"    # I

    .prologue
    .line 83
    iget-object v0, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-gt p1, v0, :cond_0

    const/4 v0, 0x0

    .line 84
    :goto_0
    return v0

    :cond_0
    const/4 v0, 0x1

    goto :goto_0
.end method

.method public setSelectedChannel(IZ)V
    .locals 0
    .param p1, "selectedChannel"    # I
    .param p2, "selectedDefault"    # Z

    .prologue
    .line 104
    iput p1, p0, Lstarcom/snd/array/ChannelList;->selectedChannel:I

    .line 105
    iput-boolean p2, p0, Lstarcom/snd/array/ChannelList;->selectedDefault:Z

    .line 106
    return-void
.end method

.method public writeChannels(Landroid/app/Activity;Ljava/lang/String;)V
    .locals 2
    .param p1, "activity"    # Landroid/app/Activity;
    .param p2, "channels_filename"    # Ljava/lang/String;

    .prologue
    .line 193
    const-string/jumbo v1, "channel_list.properties"

    invoke-virtual {p2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    iget-object v1, p0, Lstarcom/snd/array/ChannelList;->channels_custom:Ljava/util/ArrayList;

    invoke-static {v1}, Lstarcom/snd/array/ChannelList;->getStringFromChannels(Ljava/util/ArrayList;)Ljava/lang/String;

    move-result-object v0

    .line 195
    .local v0, "toWrite":Ljava/lang/String;
    :goto_0
    invoke-static {p1, v0, p2}, Lstarcom/snd/array/ChannelList;->doWriteChannels(Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;)V

    .line 196
    return-void

    .line 194
    .end local v0    # "toWrite":Ljava/lang/String;
    :cond_0
    iget-object v1, p0, Lstarcom/snd/array/ChannelList;->channels_default:Ljava/util/ArrayList;

    invoke-static {v1}, Lstarcom/snd/array/ChannelList;->getStringFromChannels(Ljava/util/ArrayList;)Ljava/lang/String;

    move-result-object v0

    .restart local v0    # "toWrite":Ljava/lang/String;
    goto :goto_0
.end method
