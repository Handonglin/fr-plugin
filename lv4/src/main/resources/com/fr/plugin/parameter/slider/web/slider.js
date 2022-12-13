(function ($) {
    FR.ParameterSlider = FR.extend(FR.Widget, {
        _defaultConfig: function () {
            return $.extend(FR.ParameterSlider.superclass._defaultConfig.apply(), {
                showWidth: 500,
                showHeight: 280,
                defaultWidth: 80
            });
        },
        _init: function () {
            FR.ParameterSlider.superclass._init.apply(this, arguments);
            var opts = this.options;
            var normalSingleSlider = BI.createWidget({
                type: "bi.single_slider",
                width: opts.width,
                height: opts.height,
                cls: "layout-bg-white",
                digit: opts.decimal,
                unit: opts.unit,
                min: opts.minValue,
                max: opts.maxValue,
            });

            //有坑啊这里，得放到上面去，但这里留一份吧
            normalSingleSlider.setMinAndMax({
                min: opts.minValue,
                max: opts.maxValue,
            });
            normalSingleSlider.setValue(opts.initValue);
            normalSingleSlider.populate();
            this.loader = normalSingleSlider;
            BI.createWidget({
                type: "bi.vertical",
                element: this.element,
                items: [{
                    type: "bi.center_adapt",
                    items: [{
                        el: normalSingleSlider
                    }]
                }]
            });
        },

        getValue: function () {
            return this.loader.getValue();
        },

        setValue: function (value) {
            this.loader.setValue(value);
        },

        setText: function (text) {
            this.editComp.val(text);
        },
        getText: function () {
            return this.editComp.val();
        }
    });
    $.shortcut('parameter.slider', FR.ParameterSlider);
})(jQuery);