import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Pagination from "react-js-pagination";

class PaginationComp extends Component {
  render() {
    const {
      isLoading,
      activePage,
      itemsCountPerPage,
      totalItemsCount,
    } = this.props;

    const pagination = (isLoading) => {
      if (!isLoading)
        return (
          <div className="d-flex justify-content-center">
            <Pagination
              activeClass="active"
              hideNavigation={false}
              activePage={activePage}
              itemsCountPerPage={itemsCountPerPage}
              totalItemsCount={totalItemsCount}
              pageRangeDisplayed={10}
              itemClass="page-item"
              linkClass="page-link"
              firstPageText="First"
              lastPageText="Last"
              nextPageText="Next"
              prevPageText="Prev"
              onChange={this.props.onChange}
            />
          </div>
        );
      else return null;
    };
    return pagination(isLoading);
  }
}
export default PaginationComp;
