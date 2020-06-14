import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { getBacklog } from "../../actions/backlogActions";
import SpinnerLoading from "../SpinnerLoading";

class ProjectBoard extends Component {
  constructor(props) {
    super(props);

    this.state = {
      errors: {},
      isLoading: true,
    };
  }

  setStateAsync(state) {
    return new Promise((resolve) => {
      this.setState(state, resolve);
    });
  }

  async componentDidMount() {
    const { id } = this.props.match.params;
    await this.props.getBacklog(id);
    const { isLoading } = this.props.backlog;
    await this.setStateAsync({
      isLoading: isLoading,
    });

    // setTimeout(
    //   function () {
    //     this.setState({ isLoading: false });
    //   }.bind(this),
    //   1000
    // );
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({
        errors: nextProps.errors,
      });
    }
  }

  newProjectTaskOnClick(errors, e) {
    if (errors.projectIdentifier) {
      alert(errors.projectIdentifier);
      e.preventDefault();
    }
  }

  render() {
    const { id } = this.props.match.params;
    const { project_tasks } = this.props.backlog; // ini cara assign variable reducer yang kedua
    const { errors } = this.state;

    let BoardContent;

    const boardAlgorithm = (errors, project_tasks, isLoading) => {
      if (
        project_tasks &&
        project_tasks !== undefined &&
        project_tasks.length < 1 &&
        !isLoading
      ) {
        if (errors.projectNotFound) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectNotFound}
            </div>
          );
        } else if (errors.projectIdentifier) {
          return (
            <div className="alert alert-danger text-center" role="alert">
              {errors.projectIdentifier}
            </div>
          );
        } else {
          return (
            <div className="alert alert-info text-center" role="alert">
              No Project Tasks on this board
            </div>
          );
        }
      } else {
        if (project_tasks && project_tasks !== undefined && !isLoading) {
          return <Backlog project_tasks_prop={project_tasks} />;
        }
      }
    };

    BoardContent = boardAlgorithm(errors, project_tasks, this.state.isLoading);

    return (
      <div className="container">
        <Link
          to={`/addProjectTask/${id}`}
          className="btn btn-primary mb-3"
          onClick={this.newProjectTaskOnClick.bind(this, errors)}
        >
          <i className="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        <SpinnerLoading isLoading={this.state.isLoading} />
        {BoardContent}
      </div>
    );
  }
}

ProjectBoard.propTypes = {
  backlog: PropTypes.object.isRequired,
  getBacklog: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  backlog: state.backlog,
  errors: state.errors,
});

export default connect(mapStateToProps, { getBacklog })(ProjectBoard);
