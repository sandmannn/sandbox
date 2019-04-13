import './styles/some.css';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');


class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {movies: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/movies'}).done(response => {
			this.setState({movies: response.entity._embedded.movies});
		});
	}

	render() {
		return (
			<MovieList movies={this.state.movies}/>
		)
	}
}

class MovieList extends React.Component{
	render() {
        console.log(this.props);
		const movies = this.props.movies.map(movie =>
			<Movie key={movie._links.self.href} movie={movie}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Name</th>
						<th>imdbLink</th>
						<th>Description</th>
					</tr>
					{movies}
				</tbody>
			</table>
		)
	}
}


class Movie extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.movie.name}</td>
				<td>{this.props.movie.imdbLink}</td>
				<td>{this.props.movie.description}</td>
			</tr>
		)
	}
}


ReactDOM.render(
	<App />,
	document.getElementById('react')
)