.class LSampleCodeForThesis;
.super Ljava/lang/Object;

.field accounts:Ljava/util/List;

.method public constructor <init>()V
    .locals 2

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private initAccountSpinner(Landroid/widget/Spinner;)V
  .locals 3
  new-instance v0, Landroid/widget/ArrayAdapter;

  iget-object v1, p0, LSampleCodeForThesis;->accounts:Ljava/util/List;

  const v2, 0x7f0a002a

  invoke-direct {v0, p0, v2, v1}, Landroid/widget/ArrayAdapter;-><init>(Landroid/content/Context;ILjava/util/List;)V

  invoke-virtual {p1, v0}, Landroid/widget/Spinner;->setAdapter(Landroid/widget/SpinnerAdapter;)V

  const/16 v2, 0x8

  invoke-virtual {v0, v2}, Landroid/widget/Spinner;->setVisibility(I)V

  return-void
.end method