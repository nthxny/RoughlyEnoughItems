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

package me.shedaniel.rei.impl.client.gui.config.options;

import me.shedaniel.rei.impl.client.config.ConfigObjectImpl;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class CompositeOption<T> {
    private final Component name;
    private final Component description;
    private final Function<ConfigObjectImpl, T> bind;
    private OptionValueEntry<T> entry;
    
    public CompositeOption(Component name, Component description, Function<ConfigObjectImpl, T> bind) {
        this.name = name;
        this.description = description;
        this.bind = bind;
    }
    
    public CompositeOption<T> entry(OptionValueEntry<T> entry) {
        this.entry = entry;
        return this;
    }
    
    public CompositeOption<Boolean> ofBoolean(Component falseText, Component trueText) {
        return ((CompositeOption<Boolean>) this).entry(OptionValueEntry.ofBoolean(falseText, trueText));
    }
    
    public CompositeOption<Boolean> trueFalse() {
        return ((CompositeOption<Boolean>) this).entry(OptionValueEntry.trueFalse());
    }
    
    public CompositeOption<Boolean> enabledDisabled() {
        return ((CompositeOption<Boolean>) this).entry(OptionValueEntry.enabledDisabled());
    }
}
