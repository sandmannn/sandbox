import './styles/some.css';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
const follow = require('./follow');
const root = '/api'


class App extends React.Component {

	constructor(props) {
		super(props);
        this.state = {movies: [], attributes: [], pageSize: 3, links: {}};
        this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onDelete = this.onDelete.bind(this);
        this.onNavigate = this.onNavigate.bind(this);
        
	}

	componentDidMount() {
        this.loadFromServer(this.state.pageSize);

	}

    loadFromServer(pageSize) {
		follow(client, root, [
			{rel: 'movies', params: {size: pageSize}}]
		).then(movieCollection => {
			return client({
				method: 'GET',
				path: movieCollection.entity._links.profile.href,
				headers: {'Accept': 'application/schema+json'}
			}).then(schema => {
				this.schema = schema.entity;
				return movieCollection;
			});
		}).done(movieCollection => {
			this.setState({
				movies: movieCollection.entity._embedded.movies,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: movieCollection.entity._links});
		});
	}

	render() {
		return (
            <div>
                <CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
                <MovieList movies={this.state.movies}
                    links={this.state.links}
                    pageSize={this.state.pageSize}
                    onNavigate={this.onNavigate}
                    onDelete={this.onDelete}
                    updatePageSize={this.updatePageSize}
                />
            </div>
		)
    }
	onCreate(newEmployee) {
		follow(client, root, ['movies']).then(employeeCollection => {
			return client({
				method: 'POST',
				path: employeeCollection.entity._links.self.href,
				entity: newEmployee,
				headers: {'Content-Type': 'application/json'}
			})
		}).then(response => {
			return follow(client, root, [
				{rel: 'movies', params: {'size': this.state.pageSize}}]);
		}).done(response => {
			if (typeof response.entity._links.last !== "undefined") {
				this.onNavigate(response.entity._links.last.href);
			} else {
				this.onNavigate(response.entity._links.self.href);
			}
		});
	}

    onDelete(movie) {
		client({method: 'DELETE', path: movie._links.self.href}).done(response => {
			this.loadFromServer(this.state.pageSize);
		});
    }
    onNavigate(navUri) {
		client({method: 'GET', path: navUri}).done(movieCollection => {
			this.setState({
				movies: movieCollection.entity._embedded.movies,
				attributes: this.state.attributes,
				pageSize: this.state.pageSize,
				links: movieCollection.entity._links
			});
		});
	}   
    updatePageSize(pageSize) {
        if (pageSize !== this.state.pageSize) {
            this.loadFromServer(pageSize);
        }
    }

}

class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		const newMovie = {};
		this.props.attributes.forEach(attribute => {
			newMovie[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onCreate(newMovie);

		// clear out the dialog's inputs
		this.props.attributes.forEach(attribute => {
			ReactDOM.findDOMNode(this.refs[attribute]).value = '';
		});

		// Navigate away from the dialog to hide it.
		window.location = "#";
	}

	render() {
		const inputs = this.props.attributes.map(attribute =>
			<p key={attribute}>
				<input type="text" placeholder={attribute} ref={attribute} className="field"/>
			</p>
		);

		return (
			<div>
				<a href="#createMovie">Create</a>

				<div id="createMovie" className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Add new movie</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Create</button>
						</form>
					</div>
				</div>
			</div>
		)
	}

}

class MovieList extends React.Component{

    constructor(props) {
		super(props);
		this.handleNavFirst = this.handleNavFirst.bind(this);
		this.handleNavPrev = this.handleNavPrev.bind(this);
		this.handleNavNext = this.handleNavNext.bind(this);
		this.handleNavLast = this.handleNavLast.bind(this);
		this.handleInput = this.handleInput.bind(this);
	}

    handleInput(e) {
        e.preventDefault();
        const pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
        if (/^[0-9]+$/.test(pageSize)) {
            this.props.updatePageSize(pageSize);
        } else {
            ReactDOM.findDOMNode(this.refs.pageSize).value =
                pageSize.substring(0, pageSize.length - 1);
        }
    }
    handleNavFirst(e){
		e.preventDefault();
		this.props.onNavigate(this.props.links.first.href);
	}

	handleNavPrev(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.prev.href);
	}

	handleNavNext(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.next.href);
	}

	handleNavLast(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.last.href);
	}

    render() {
        console.log(this.props);
		const movies = this.props.movies.map(movie =>
			<Movie key={movie._links.self.href} movie={movie} onDelete={this.props.onDelete}/>
		);

		const navLinks = [];
		if ("first" in this.props.links) {
			navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
		}
		if ("prev" in this.props.links) {
			navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
		}
		if ("next" in this.props.links) {
			navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
		}
		if ("last" in this.props.links) {
			navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
		}

        return (
            <div>
                <input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>

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
                <div>
					{navLinks}
				</div>
            </div>
		)
    }

}


class Movie extends React.Component{

	handleDelete() {
		this.props.onDelete(this.props.movie);
	}

	constructor(props) {
		super(props);
		this.handleDelete = this.handleDelete.bind(this);
	}

	render() {
		return (
			<tr>
				<td>{this.props.movie.name}</td>
				<td>{this.props.movie.imdbLink}</td>
				<td>{this.props.movie.description}</td>
                <td>
					<button onClick={this.handleDelete}>Delete</button>
				</td>
			</tr>
		)
	}
}


ReactDOM.render(
	<App />,
	document.getElementById('react')
)