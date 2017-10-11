import React, {Component} from "react";

class InputError extends Component {

  render() {
    if (this.props.visible) {
      return (
        <div className="error">
          <span>{this.props.errorMessage}</span>
        </div>
      )
    }
    else {
      return (<div/>)
    }
  }

}

export default InputError;