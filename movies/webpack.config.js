var path = require('path');
// var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
const JS_JSX_PATTERN = /\.jsx?$/;

module.exports = {
    entry:  [
        './src/main/js/app.js'
        // './src/bundle.1.js'
        
        // 'webpack-dev-server/client?http://localhost:3000',
        // 'webpack/hot/only-dev-server',
        // './src/index'
      ],
    devtool: 'sourcemaps',
    // cache: true,
    mode: 'development',
    devServer: {
        contentBase: path.join(__dirname, '/src/main/resources/static/built/'),
        // contentBase: './src',
        // contentBase: './public',
        // filename: './src/main/resources/static/built/bundle.js',
        // hot: true,
        // publicPath: '/'

        // compress: true,
        port: 3000,
        proxy: {
            '/api': 'http://localhost:8080'
          }
    },
    output: {
        path: path.join(__dirname, '/src/main/resources/static/built/'),
        // path: "./src/main/resources/static/built/",
        // path: __dirname + 'src/',
        // filename: './src/main/resources/static/built/bundle.js',
        // filename: 'bundle.js',
        // publicPath: 'http://localhost:3000/'
        // publicPath: '/'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                test: JS_JSX_PATTERN,
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },
            {
                test:/\.css$/,
                use:['style-loader','css-loader']
            }
        ]
    },
    plugins: [new HtmlWebpackPlugin({
        template: './src/main/resources/templates/index.html'
    })]

};