package uz.oltinolma.producer.files.dao;


import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.name.Rename;

public class RenameU extends Rename {

    public static final Rename PREFIX_800x800 = new Rename() {
        public String apply(String var1, ThumbnailParameter var2) {
            return this.appendPrefix(var1, "800x800.");
        }
    };

    @Override
    public String apply(String s, ThumbnailParameter thumbnailParameter) {
        return null;
    }
}
