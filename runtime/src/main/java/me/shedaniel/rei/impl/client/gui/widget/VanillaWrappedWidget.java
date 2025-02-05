/*
 * This file is licensed under the MIT License, part of Roughly Enough Items.
 * Copyright (c) 2018, 2019, 2020, 2021, 2022, 2023 shedaniel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.shedaniel.rei.impl.client.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class VanillaWrappedWidget extends Widget {
    private GuiEventListener element;
    
    public VanillaWrappedWidget(GuiEventListener element) {
        this.element = Objects.requireNonNull(element);
        setFocused(element);
    }
    
    @Override
    public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
        if (element instanceof Widget widget) {
            widget.setZ(getZ());
            widget.render(matrices, mouseX, mouseY, delta);
        } else {
            matrices.pushPose();
            matrices.translate(0, 0, getZ());
            if (element instanceof Renderable widget)
                widget.render(matrices, mouseX, mouseY, delta);
            matrices.popPose();
        }
    }
    
    @Override
    public List<? extends GuiEventListener> children() {
        return Collections.singletonList(element);
    }
    
    @Nullable
    @Override
    public GuiEventListener getFocused() {
        return element;
    }
    
    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {
        if (guiEventListener == element) {
            super.setFocused(element);
        } else if (element instanceof ContainerEventHandler handler) {
            handler.setFocused(guiEventListener);
        }
    }
    
    @Override
    public boolean isDragging() {
        return true;
    }
    
    @Override
    public boolean containsMouse(double mouseX, double mouseY) {
        return element.isMouseOver(mouseX, mouseY);
    }
}