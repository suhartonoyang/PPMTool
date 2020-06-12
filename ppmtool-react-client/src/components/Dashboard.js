import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";
import "bootstrap/dist/css/bootstrap.min.css";
import CreateProjectButton from "./Project/CreateProjectButton";
import { connect } from "react-redux";
import { getProjects } from "../actions/projectActions";
import PropTypes from "prop-types";

class Dashboard extends Component {
  componentDidMount() {
    this.props.getProjects();
  }

  render() {
    const { projects } = this.props.project; // ini cara assign variable reducer state yang kedua

    let dashboardContent;

    const dashboardAlgo = (projects) => {
      if (projects.length < 1) {
        return (
          <div className="alert alert-info text-center" role="alert">
            No Projects on dashboard
          </div>
        );
      } else {
        return projects.map((project) => (
          <ProjectItem key={project.id} project={project} />
        ));
      }
    };

    dashboardContent = dashboardAlgo(projects);

    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Projects</h1>
              <br />
              <CreateProjectButton />
              <br />
              <hr />
              {dashboardContent}
              {
                //   {projects.map((project) => (
                //   <ProjectItem key={project.id} project={project} />
                // ))}
              }
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propTypes = {
  project: PropTypes.object.isRequired,
  getProjects: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => ({
  project: state.project, //sesuai dengan index di reducers
});

export default connect(mapStateToProps, { getProjects })(Dashboard);
