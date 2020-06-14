import {
  GET_PROJECTS,
  GET_PROJECT,
  DELETE_PROJECT,
  GET_PROJECTS_PAGING,
} from "../actions/types";

const initialState = {
  projects: [],
  project: {},
  projectPaging: {},
  isLoading: true,
};

export default function (state = initialState, action) {
  switch (action.type) {
    case GET_PROJECTS:
      return {
        ...state,
        projects: action.payload,
      };

    case GET_PROJECTS_PAGING:
      return {
        ...state,
        projectPaging: action.payload,
        isLoading: false,
      };

    case GET_PROJECT:
      return {
        ...state,
        project: action.payload,
      };

    case DELETE_PROJECT:
      return {
        ...state,
        projects: state.projects.filter(
          (project) => project.projectIdentifier !== action.payload
        ),
      };
    default:
      return state;
  }
}
