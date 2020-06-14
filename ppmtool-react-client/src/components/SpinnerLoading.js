import React, { Component } from "react";

class SpinnerLoading extends Component {
  render() {
    const isLoading = this.props.isLoading;
    const spinnerLoading = (isLoading) => {
      if (isLoading) {
        return (
          <div className="d-flex justify-content-center">
            <div className="spinner-border" role="status">
              <span className="sr-only">Loading...</span>
            </div>
            <br />
            <br />
          </div>
        );
      } else {
        return null;
      }
    };
    return spinnerLoading(isLoading);
  }
}

export default SpinnerLoading;
