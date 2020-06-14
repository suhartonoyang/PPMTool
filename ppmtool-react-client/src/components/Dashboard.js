import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";
import "bootstrap/dist/css/bootstrap.min.css";
import CreateProjectButton from "./Project/CreateProjectButton";
import { connect } from "react-redux";
import { getProjects, getProjectsPaging } from "../actions/projectActions";
import PropTypes from "prop-types";
import Pagination from "react-js-pagination";
import SpinnerLoading from "./SpinnerLoading";
import PaginationComp from "./PaginationComp";

class Dashboard extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: null,
      activePage: 0,
      totalPages: 0,
      itemsCountPerPage: 0,
      totalItemsCount: 0,
      render: false,
      isLoading: true,
    };

    this.handlePageChange = this.handlePageChange.bind(this);
    this.fetchUrl = this.fetchUrl.bind(this);
  }

  setStateAsync(state) {
    return new Promise((resolve) => {
      this.setState(state, resolve);
    });
  }

  async fetchUrl(pages) {
    await this.props.getProjectsPaging(pages, 5);
    const {
      content,
      totalPages,
      totalElements,
      size,
    } = this.props.project.projectPaging;

    const { isLoading } = this.props.project;

    await this.setStateAsync({
      content: content,
      totalPages: totalPages,
      itemsCountPerPage: size,
      totalItemsCount: totalElements,
      isLoading: isLoading,
    });
  }

  async componentDidMount() {
    // this.props.getProjects();

    await this.fetchUrl(this.state.activePage);
    // setTimeout(
    //   function () {
    //     this.setState({ isLoading: false });
    //   }.bind(this),
    //   3000
    // );

    // console.log(this.state.totalPages);
    // console.log(this.state.totalItemsCount);
    // console.log(this.state.itemsCountPerPage);
    // console.log(this.state.content);
  }

  handlePageChange(pageNumber) {
    console.log(`active page is ${pageNumber}`);
    this.setState({ activePage: pageNumber - 1 });
    this.fetchUrl(pageNumber - 1);
  }

  render() {
    // const { projects } = this.props.project; // ini cara assign variable reducer state yang kedua
    let dashboardContent;

    const dashboardAlgo = (project, isLoading) => {
      if (
        project &&
        project !== undefined &&
        project.length < 1 &&
        !isLoading
      ) {
        return (
          <div className="alert alert-info text-center" role="alert">
            No Projects on dashboard
          </div>
        );
      } else {
        if (project && project !== undefined && !isLoading) {
          {
            //   {projects.map((project) => (
            //   <ProjectItem key={project.id} project={project} />
            // ))}
          }

          return project.map((project) => (
            <ProjectItem key={project.id} project={project} />
          ));
        }
      }
    };

    dashboardContent = dashboardAlgo(this.state.content, this.state.isLoading);

    // const pagination = (isLoading) => {
    //   if (!isLoading)
    //     return (
    //       <div className="d-flex justify-content-center">
    //         <Pagination
    //           activeClass="active"
    //           hideNavigation={false}
    //           activePage={this.state.activePage + 1}
    //           itemsCountPerPage={this.state.itemsCountPerPage}
    //           totalItemsCount={this.state.totalItemsCount}
    //           pageRangeDisplayed={10}
    //           itemClass="page-item"
    //           linkClass="page-link"
    //           firstPageText="First"
    //           lastPageText="Last"
    //           nextPageText="Next"
    //           prevPageText="Prev"
    //           onChange={this.handlePageChange}
    //         />
    //       </div>
    //     );
    // };

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
              {
                //   {projects.map((project) => (
                //   <ProjectItem key={project.id} project={project} />
                // ))}
              }

              <SpinnerLoading isLoading={this.state.isLoading} />

              {dashboardContent}

              <PaginationComp
                isLoading={this.state.isLoading}
                activePage={this.state.activePage + 1}
                itemsCountPerPage={this.state.itemsCountPerPage}
                totalItemsCount={this.state.totalItemsCount}
                onChange={this.handlePageChange}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propTypes = {
  project: PropTypes.object.isRequired,
  getProjectsPaging: PropTypes.func.isRequired,
  getProjects: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => ({
  project: state.project, //sesuai dengan index di reducers
});

export default connect(mapStateToProps, { getProjects, getProjectsPaging })(
  Dashboard
);
