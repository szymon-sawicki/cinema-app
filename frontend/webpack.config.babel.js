import path from 'path';
import HtmlWebpackPlugin from 'html-webpack-plugin';

// W ten sposob z tego pliku eksportujesz specjalny obiekt konfiguracyjny
// ktory "pod spodem" przechwyci webpack i juz bedzie wiedzial co z nim ma zrobic
module.exports = {
    // Podajesz ktore pliki js maja byc przebudowane przez babel do postaci natywnej
    // Pliki podajesz w postaci par <chunk>: <sciezka>
    entry: {
        index: './src/js/index.js'
    },

    module: {
        // Tutaj okreslasz wszelkie operacje ktore zamierzasz na plikach wejsciowych
        // w celu przeksztalcenia ich do pewnej postaci wyjsciowej
        rules: [
            // Tutaj robimy konfiguracje ktora pozwoli przebudowac pliki js z sekcji
            // entry do postaci wynikowej zapisanej tam gdzie skonfigurowano w output
            {
                test: /\.(js)$/,
                exclude: /node_modules/,
                use: ['babel-loader']
            }
        ]
    },

    // Sekcja w ktorej okreslasz gdzie ma zostac zapisany plik po przekompilowaniu przez
    // babel
    // Sciezke okreslisz ogolnie dla wszystkich plikow
    // __dirname to zaszyta w node stala ktora zwraca sciezke katalogu twojego projektu
    output: {
        path: path.resolve(__dirname, './dist'),
        filename: './js/[name].bundle.js'
    },

    plugins: [
        new HtmlWebpackPlugin({
            // Jak ma sie nazywac plik wynikowy po zaladowaniu do dist
            filename: 'index.html',
            title: 'INDEX',
            // Ktory plik wejsciowy ma byc ladowany tym pluginem
            template: path.resolve(__dirname, './src/index.html'),
            // Teraz mowimy ktory plik lub pliki js ma byc dolaczony do pliku html
            chunks: ['index']
        })
    ],

    mode: "development"
}
