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

package me.shedaniel.rei.impl.client.gui.config;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.utils.value.IntValue;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.impl.client.gui.config.components.ConfigCategoriesListWidget;
import me.shedaniel.rei.impl.client.gui.config.options.ConfigCategories;
import me.shedaniel.rei.impl.client.gui.config.options.OptionCategory;
import me.shedaniel.rei.impl.client.gui.credits.CreditsScreen;
import me.shedaniel.rei.impl.client.gui.widget.HoleWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;

public class REIConfigScreen extends Screen {
    private final Screen parent;
    private final List<OptionCategory> categories;
    private final List<Widget> widgets = new ArrayList<>();
    private OptionCategory activeCategory;
    
    public REIConfigScreen(Screen parent) {
        this(parent, ConfigCategories.CATEGORIES);
    }
    
    public REIConfigScreen(Screen parent, List<OptionCategory> categories) {
        super(new TranslatableComponent("config.roughlyenoughitems.title"));
        this.parent = parent;
        this.categories = categories;
        Preconditions.checkArgument(!categories.isEmpty(), "Categories cannot be empty!");
        this.activeCategory = categories.get(0);
    }
    
    @Override
    public void init() {
        super.init();
        this.widgets.clear();
        this.widgets.add(Widgets.createButton(new Rectangle(4, 4, 100, 20), new TranslatableComponent("text.rei.credits"))
                .onClick(button -> {
                    Minecraft.getInstance().setScreen(new CreditsScreen(this));
                }));
        this.widgets.add(Widgets.createLabel(new Point(width / 2, 12), this.title));
        int sideWidth = (int) (width / 3.8);
        this.widgets.add(ConfigCategoriesListWidget.create(new Rectangle(8, 32, sideWidth, height - 32 - 32), categories, new IntValue() {
            @Override
            public void accept(int i) {
                REIConfigScreen.this.activeCategory = categories.get(i);
            }
            
            @Override
            public int getAsInt() {
                return categories.indexOf(activeCategory);
            }
        }));
        this.widgets.add(HoleWidget.create(new Rectangle(12 + sideWidth, 32, width - 20 - sideWidth, height - 32 - 32), () -> 0, 32));
    }
    
    @Override
    public void render(PoseStack poses, int mouseX, int mouseY, float delta) {
        this.renderDirtBackground(0);
        super.render(poses, mouseX, mouseY, delta);
        for (Widget widget : widgets) {
            widget.render(poses, mouseX, mouseY, delta);
        }
    }
    
    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
    
    @Override
    public List<? extends GuiEventListener> children() {
        return (List<? extends GuiEventListener>) (List<?>) this.widgets;
    }
    
    @Override
    public boolean charTyped(char character, int modifiers) {
        for (GuiEventListener listener : children())
            if (listener.charTyped(character, modifiers))
                return true;
        return super.charTyped(character, modifiers);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (GuiEventListener entry : children())
            if (entry.mouseDragged(mouseX, mouseY, button, deltaX, deltaY))
                return true;
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (GuiEventListener entry : children())
            if (entry.mouseReleased(mouseX, mouseY, button))
                return true;
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        for (GuiEventListener listener : children())
            if (listener.mouseScrolled(mouseX, mouseY, amount))
                return true;
        return super.mouseScrolled(mouseX, mouseY, amount);
    }
}
