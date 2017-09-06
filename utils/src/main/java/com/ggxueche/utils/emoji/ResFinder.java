package com.ggxueche.utils.emoji;

/**
 *
 * Created by fcx on 2017/3/3.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class ResFinder {
    private static Map<ResItem, Integer> a = new HashMap();
    private static final int b = -1;
    private static Context c;
    private static String d = "";

    public ResFinder() {
    }

    public static void initContext(Context var0) {
        c = var0;
        if(c == null) {
            throw new NullPointerException("初始化ResFinder失败，传递的Context为空.");
        } else {
            d = c.getPackageName();
        }
    }

    public static void setPackageName(String var0) {
        d = var0;
    }

    public static Context getApplicationContext() {
        return c;
    }

    public static int getResourceId(ResFinder.ResType var0, String var1) {
        ResFinder.ResItem var2 = new ResFinder.ResItem(var0, var1);
        int var3 = a(var2);
        if(var3 != -1) {
            return var3;
        } else {
            Resources var4 = c.getResources();
            var3 = var4.getIdentifier(var1, var0.toString(), d);
            if(var3 <= 0) {
                throw new RuntimeException("获取资源ID失败:(packageName=" + d + " type=" + var0 + " name=" + var1 + ", 请确保的res/" + var0.toString() + "目录中含有该资源");
            } else {
                a.put(var2, Integer.valueOf(var3));
                return var3;
            }
        }
    }

    private static int a(ResFinder.ResItem var0) {
        return a.containsKey(var0)?((Integer)a.get(var0)).intValue():-1;
    }

    public static String getString(String var0) {
        int var1 = getResourceId(ResFinder.ResType.STRING, var0);
        return c.getString(var1);
    }

    public static int getLayout(String var0) {
        return getResourceId(ResFinder.ResType.LAYOUT, var0);
    }

    public static int getColor(String var0) {
        return c.getResources().getColor(getResourceId(ResFinder.ResType.COLOR, var0));
    }

    public static int getId(String var0) {
        return getResourceId(ResFinder.ResType.ID, var0);
    }

    public static int getStyle(String var0) {
        return getResourceId(ResFinder.ResType.STYLE, var0);
    }

    public static int getStyleableId(String var0) {
        return getResourceId(ResFinder.ResType.STYLEABLE, var0);
    }

    public static int[] getStyleableArrts(String var0) {
        return a(c, var0);
    }

    public static float getDimen(String var0) {
        return c.getResources().getDimension(getResourceId(ResFinder.ResType.DIMEN, var0));
    }

    public static Drawable getDrawable(String var0) {
        return c.getResources().getDrawable(getResourceId(ResFinder.ResType.DRAWABLE, var0));
    }

    private static final int[] a(Context var0, String var1) {
        try {
            Field[] var2 = Class.forName(var0.getPackageName() + ".R$styleable").getFields();
            Field[] var3 = var2;
            int var4 = var2.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Field var6 = var3[var5];
                if(var6.getName().equals(var1)) {
                    int[] var7 = (int[])((int[])var6.get((Object)null));
                    return var7;
                }
            }
        } catch (Throwable var8) {
            var8.printStackTrace();
        }

        return null;
    }

    public static int getStyleableIndex(String var0) {
        int var1 = 0;

        try {
            Field[] var2 = Class.forName(c.getPackageName() + ".R$styleable").getFields();

            for(int var3 = 0; var3 < var2.length; ++var3) {
                Field var4 = var2[var3];
                if(var4.getName().equals(var0)) {
                    var1 = var3;
                    break;
                }
            }
        } catch (Throwable var5) {
            var5.printStackTrace();
        }

        return var1 - 1;
    }

    public static class ResItem {
        public ResFinder.ResType mType;
        public String mName;

        public ResItem(ResFinder.ResType var1, String var2) {
            this.mType = var1;
            this.mName = var2;
        }

        public int hashCode() {
            boolean var1 = true;
            byte var2 = 1;
            int var3 = 31 * var2 + (this.mName == null?0:this.mName.hashCode());
            var3 = 31 * var3 + (this.mType == null?0:this.mType.toString().hashCode());
            return var3;
        }

        public boolean equals(Object var1) {
            if(this == var1) {
                return true;
            } else if(var1 == null) {
                return false;
            } else if(this.getClass() != var1.getClass()) {
                return false;
            } else {
                ResFinder.ResItem var2 = (ResFinder.ResItem)var1;
                if(this.mName == null) {
                    if(var2.mName != null) {
                        return false;
                    }
                } else if(!this.mName.equals(var2.mName)) {
                    return false;
                }

                return this.mType == var2.mType;
            }
        }
    }

    public static enum ResType {
        LAYOUT {
            public String toString() {
                return "layout";
            }
        },
        ID {
            public String toString() {
                return "id";
            }
        },
        DRAWABLE {
            public String toString() {
                return "drawable";
            }
        },
        STYLE {
            public String toString() {
                return "style";
            }
        },
        STYLEABLE {
            public String toString() {
                return "styleable";
            }
        },
        STRING {
            public String toString() {
                return "string";
            }
        },
        COLOR {
            public String toString() {
                return "color";
            }
        },
        DIMEN {
            public String toString() {
                return "dimen";
            }
        },
        RAW {
            public String toString() {
                return "raw";
            }
        },
        ANIM {
            public String toString() {
                return "anim";
            }
        },
        ARRAY {
            public String toString() {
                return "array";
            }
        };

        private ResType() {
        }
    }
}
