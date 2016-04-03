# mondriandroid

A Clojure program for programmatically generating SVGs of art in the style of [Piet Mondrian](https://en.wikipedia.org/wiki/Piet_Mondrian).

## Usage

Clone this repository and start the development server with `lein ring server`. This will open a browser displaying a generated image. Reloading the page will generate a new image. If you've edited the image generation code you'll see the results in the browser.

The high-level code for image generation is in `mondriandroid/generate.clj`. Colour controls are in `mondriandroid/colour.clj`. Low level  components  are in `mondriandroid/rect.clj` and `mondriandroid/draw.clj`, which might need to be modified or extended for partiular generation strategies.

## License

Copyright 2016 Nathaniel Knight. Provided under the [MIT license](https://opensource.org/licenses/MIT).
