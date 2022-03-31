package cc.woverflow.debugify.mixins.client.mc140646;

import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Taken from TieFix
 * https://github.com/j-tai/TieFix
 * under LGPLv3 license
 *
 * Adapted to work in a multi-loader environment
 *
 * @author j-tai
 */
@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin {
    @Shadow
    private boolean selecting;
    @Shadow
    private int selectionEnd;

    @Shadow
    public abstract void setSelectionEnd(int index);

    /**
     * Scrolling logic is in {@code setSelectionEnd} so we call it
     * and don't let the method actually modify the selectionEnd
     */
    @Inject(method = "setCursor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;setSelectionStart(I)V", shift = At.Shift.AFTER))
    private void onSetCursor(int cursor, CallbackInfo ci) {
        if (selecting) {
            int end = selectionEnd;
            setSelectionEnd(cursor);
            selectionEnd = end;
        }
    }

    /**
     * Prevent the substring end index being less than 0,
     * causing a crash.
     */
    @ModifyArg(method = "renderButton", at = @At(value = "INVOKE", target = "Ljava/lang/String;substring(II)Ljava/lang/String;", ordinal = 1), index = 1)
    private int boundSelectionEnd(int relativeSelectionEnd) {
        return Math.max(0, relativeSelectionEnd);
    }
}
