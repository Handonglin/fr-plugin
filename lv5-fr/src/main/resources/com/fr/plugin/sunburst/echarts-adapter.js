var document = {
    createElement: function(element) {
        if (element == 'canvas') {
            return new Canvas();
        }
    },
};
